package com.svyazda.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svyazda.dtos.ProfileInfo;

public class Converter {
    public static String ObjectToJson(ProfileInfo profileInfo) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(profileInfo);
    }
}
