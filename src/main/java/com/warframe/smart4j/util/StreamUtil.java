package com.warframe.smart4j.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/15 17:02
 * <p>
 * 流操作工具类
 */
public final class StreamUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流中获取字符串
     */
    public static String getString(InputStream is) {
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            LOGGER.error("get String failure", e);
            throw new RuntimeException(e);
        }

        return sb.toString();
    }


    /**
     * 将输入流复制到输出流
     * 就是简单的读写操作
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream) {
        //还可以使用try-with-resources的形式，可以简化关闭流的操作
        try {
            int length;
            byte[] buffer = new byte[4 * 1024];
            while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer,0,length);
            }
            outputStream.flush();
        } catch (Exception e) {
            LOGGER.error("copy stream failure",e);
            throw new RuntimeException(e);
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            }catch (Exception e){
                LOGGER.error("close stream failure",e);
            }
        }
    }

}
