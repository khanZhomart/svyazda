package com.svyazda.utils;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

public class Util {

    /**
     * 
     * @return unique String id
     */
    static String getRandomGeneratedId() {
        return NanoIdUtils.randomNanoId();
    }
}