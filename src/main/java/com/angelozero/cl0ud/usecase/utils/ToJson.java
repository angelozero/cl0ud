package com.angelozero.cl0ud.usecase.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ToJson {

    public static String execute(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception ex) {
            throw new ToJsonException("Error to convert the data {} into a json", obj);
        }
    }
}
