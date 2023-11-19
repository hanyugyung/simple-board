package org.example.han.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    public static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }
}