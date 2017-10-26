package com.warframe.smart4j.helper;

import com.warframe.smart4j.bean.FileParam;
import com.warframe.smart4j.bean.FormParam;
import com.warframe.smart4j.bean.Param;
import com.warframe.smart4j.util.CollectionUtil;
import com.warframe.smart4j.util.FileUtil;
import com.warframe.smart4j.util.StreamUtil;
import com.warframe.smart4j.util.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/26 10:07
 * <p>
 * 文件上传助手类
 */
public final class UploadHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);

    //FileUpload提供的Servlet文件上传对象
    private static ServletFileUpload servletFileUpload;

    /**
     * 初始化
     */
    public static void init(ServletContext servletContext) {
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));

        int uploadLimit = ConfigHelper.getAppUploadLimit();
        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }


    /**
     * 判断请求是够为multipart类型
     */
    public static boolean isMultipart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
}

    /**
     * 创建请求对象  (Param)
     */
    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FileParam> fileParamList = new ArrayList<>();
        List<FormParam> formParamList = new ArrayList<>();

        try {
            //将request中的参数都提取出来，包括文件流等，然后放到一个Map中，本质上就是将parseRequest中获得的List<FileItem>进行进一步的映射
            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            if (CollectionUtil.isNotEmpty(fileItemListMap)) {
                for (Map.Entry<String, List<FileItem>> entry : fileItemListMap.entrySet()) {
                    String fieldName = entry.getKey();
                    List<FileItem> fileItemList = entry.getValue();
                    if (CollectionUtil.isNotEmpty(fileItemList)) {
                        for (FileItem fileItem : fileItemList) {
                            //如果是表单参数，就添加进formParamList中去
                            if (fileItem.isFormField()) {
                                String fieldValue = fileItem.getString("UTF-8");
                                formParamList.add(new FormParam(fieldName, fieldValue));
                            } else {
                                String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                                if (StringUtil.isNotEmpty(fieldName)) {
                                    //如果是文件参数，就添加到fileParamList中去
                                    long fileSize = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(fieldName, fileName, fileSize, contentType, inputStream));
                                }

                            }
                        }
                    }
                }
            }

        } catch (FileUploadException e) {
            LOGGER.error("create param failure", e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList, fileParamList);
    }

    /**
     * 真正进行上传文件的方法******划重点，前面的都是准备工作
     *
     * 我菜单都好了，就差一个厨师了
     * 就差一个程序员了     @_@
     *
     * 上传文件
     */
    public static void uploadFile(String basePath, FileParam fileParam) {
        try {
            if (fileParam != null) {
                //设置上传的文件在服务器中保存的位置
                String filePath = basePath + fileParam.getFileName();
                //先创建文件
                FileUtil.createFile(filePath);
                //创建输入流，进行读取
                InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                //创建输出流，进行写入
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream, outputStream);
            }
        } catch (Exception e) {
            LOGGER.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量上传文件
     */
    public static void uploadFile(String basePath, List<FileParam> fileParamList) {
        try {
            if (CollectionUtil.isNotEmpty(fileParamList)) {
                for (FileParam fileParam : fileParamList) {
                    uploadFile(basePath, fileParam);
                }
            }

        } catch (Exception e) {
            LOGGER.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }


}
