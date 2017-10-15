package com.warframe.smart4j.helper;

import com.warframe.smart4j.annotation.Action;
import com.warframe.smart4j.bean.Handler;
import com.warframe.smart4j.bean.Request;
import com.warframe.smart4j.util.ArrayUtil;
import com.warframe.smart4j.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/15 14:42
 * <p>
 * 需要一个ControllerHelper类，
 * 通过ClassHelper,可以获取所有定义了Controller的类，可以通过反射获取该类中所有带有Action注解的方法，获取
 * Action注解的请求表达式，进而获取请求方法和路径，封装一个请求对象(Request)与处理对象(Handler),最后将Handler和Request
 * 建立一个映射关系，放入ActionMap中，并提供一个可根据方法与请求路径获取处理对象的方法。
 */
public final class ControllerHelper {

    /**
     * 用于存放请求和处理的映射关系(ActionMap)
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        //获取所有Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            //遍历所有Controller类
            for (Class<?> controllerClass : controllerClassSet) {
                //获取controller中的所有方法
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                    //遍历所有方法
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)) {
                            //从Action注解中获取URL映射规则
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            //验证映射规则
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                                    //获取请求方法和请求路径
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod,requestPath);
                                    Handler handler = new Handler(controllerClass,method);

                                    //初始化Action Map
                                    ACTION_MAP.put(request,handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 获取Handler
     */
    public static Handler getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return  ACTION_MAP.get(request);
    }
}
