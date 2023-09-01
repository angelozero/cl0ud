package com.angelozero.cl0ud.utils;

import com.angelozero.cl0ud.exception.utils.JsonUtilsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {

    public static String generateJson(Object obj) {

        if (obj == null) {
            log.error("\n[ERROR] - JsonUtils: Error to convert object into a json string - object is null\n");
            throw new JsonUtilsException("JsonUtils: Error to convert object into a json string - object is null");
        }

        try {
            return new ObjectMapper().writeValueAsString(obj);

        } catch (Exception ex) {
            log.warn("\n[WARN] - JsonUtils: Fail to convert object into a json string - will be returned the string value\n");
            return obj.toString();
        }
    }
}
