package com.warframe.smart4j;

import com.warframe.smart4j.bean.*;
import com.warframe.smart4j.helper.*;
import com.warframe.smart4j.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/15 16:40
 * <p>
 * <p>
 * 编写一个servlet，处理所有的请求。从HttpServletRequest对象中获取请求方法和路径，通过ControllerHelper#getHandler方法
 * 获取Handler对象。
 * 当拿到Handler对象后，可以获取到Controller类及其实例对象。
 * 随后可以从HttpServletRequest对象中获取所有请求参数，并将其初始化到一个Param的对象中。
 * <p>
 * 核心类
 * <p>
 * <p>
 * 实现一个简单的mvc框架
 */

//Servlet3.0支持使用注解，可以实现web.xml零配置
//所有请求都经由DispatcherServlet进行处理(/*)
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {



    /**
     * 进行初始化，很重要
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关helper类
        HelperLoader.init();
        //获取ServletContext对象(用于注册Servlet)
        //动态注册Servlet，只能在初始化时进行注册。在运行时为了安全原因，无法完成注册,还是不是很清楚
        ServletContext servletContext = config.getServletContext();
        //注册处理JSP的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
        //文件上传功能的扩展初始化
        UploadHelper.init(servletContext);


    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //初始化
        ServletHelper.init(req, resp);
        try {
            //获取请求方法和路径
            String requestMethod = req.getMethod().toLowerCase();
            String requestPath = req.getPathInfo();

            //首先对于/favicon.ico的请求，不需要进行创建参数(静态资源)
            if (requestPath.equals("/favicon.ico")) {
                return;
            }

            //处理Action处理器
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            //存在相关映射
            if (handler != null) {
                //获取Controller类及其实例
                Class<?> controllerClass = handler.getControllerClass();
                Object controller = BeanHelper.getBean(controllerClass);

//            //创建请求参数对象
//            Map<String, Object> paramMap = new HashMap<>();
//            //获取发送请求页面中form表单里所有具有name属性的表单对象
//            Enumeration<String> paramNames = req.getParameterNames();
//            while (paramNames.hasMoreElements()) {
//                String paramName = paramNames.nextElement();
//                String paramValue = req.getParameter(paramName);
//                paramMap.put(paramName, paramValue);
//            }
//            /**
//             * 疑问，网上有人说request.getParameter和request.getInputStream不能同时使用
//             */
//            //url后面的参数
//            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
//            if (StringUtil.isNotEmpty(body)) {
//                String[] params = StringUtil.splitString(body, "&");
//                if (ArrayUtil.isNotEmpty(params)) {
//                    for (String param : params) {
//                        String[] array = StringUtil.splitString(param, "=");
//                        if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
//                            String paramName = array[0];
//                            String paramValue = array[1];
//                            paramMap.put(paramName, paramValue);
//                        }
//                    }
//                }
//            }

//            Param param = new Param(paramMap);
                /**
                 *因为对Param进行了重新设计，去除了原来的paramMap(只保存键值对的思路)
                 *
                 *
                 */
                /**
                 * 创建参数对象 Param
                 */
                Param param;
                if (UploadHelper.isMultipart(req)) {
                    param = UploadHelper.createParam(req);
                } else {
                    param = RequestHelper.createParam(req);
                }


                //调用Action方法
                Method actionMethod = handler.getActionMethod();
                Object result;
            /* 优化action参数
             * 对于有些action方法来说，根本就不需要param参数
              * */
                if (param.isEmpty()) {
                    result = ReflectionUtil.invokeMethod(controller, actionMethod);
                } else {
                    result = ReflectionUtil.invokeMethod(controller, actionMethod, param);
                }
                //处理Action方法返回值
                if (result instanceof View) {
                    //返回JSP页面
                    View view = (View) result;
                    String path = view.getPath();
                    if (StringUtil.isNotEmpty(path)) {
                        //如果path以/开头，表示重定向
                        if (path.startsWith("/")) {
                            resp.sendRedirect(req.getContextPath() + path);
                        } else {
                            //path没有以/开头,就表示跳转到一个jsp页面,并附上相关数据model
                            //这一步是为了将所需要的参数信息add到view对象中的model中以便之后进行调用
                            // (一般之后的跳转都是跳转到jsp页面，而model数据就是jsp页面中所需要呈现的数据信息)
                            Map<String, Object> model = view.getModel();
                            for (Map.Entry<String, Object> entry : model.entrySet()) {
                                //将model数据信息设置到request中，jsp可以直接调用
                                //request是jsp九大内置对象之一
                                req.setAttribute(entry.getKey(), entry.getValue());
                            }
                            //一般都是跳转到jsp页面中model信息一般存到都是jsp页面中需要显示的模型数据
                            req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                        }
                    }
                } else if (result instanceof Data) {
                    //返回JSON数据
                    Data data = (Data) result;
                    Object model = data.getModel();
                    if (null != model) {
                        resp.setContentType("application/json");
                        resp.setCharacterEncoding("UTF-8");
                        PrintWriter writer = resp.getWriter();
                        String json = JsonUtil.toJson(model);
                        //直接将json数据通过流的形式传到浏览器(或前端)
                        writer.write(json);
                        writer.flush();
                        writer.close();
                    }
                }
            }
        }finally {

            ServletHelper.destroy();
        }

    }

}
