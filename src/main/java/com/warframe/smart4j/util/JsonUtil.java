package com.warframe.smart4j.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/15 17:08
 * <p>
 * JsonUtil类用于处理JSON与POJO之间的转换，基于Jackson实现
 */
public final class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将POJO转换为JSON
     */
    public static <T> String toJson(T obj) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error("convert POJO to JSON failure", e);
            throw new RuntimeException(e);
        }

        return json;
    }

    /**
     * 将JSON转换为POJO
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch (Exception e) {

            LOGGER.error("convert JSON to POJO failure", e);
            throw new RuntimeException(e);
        }

        return pojo;
    }

}
