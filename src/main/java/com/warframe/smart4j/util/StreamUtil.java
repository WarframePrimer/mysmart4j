package com.warframe.smart4j.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
            LOGGER.error("get String failure",e);
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

}
