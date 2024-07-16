package com.example.backend.utils;

/**
 * @version 1.0.0
 * @description: The type Error util.
 * @author feixia0g
 * @date 2024/7/8 21:20
 */
public class ErrorUtil {
    /**
     * Parameter error format string.
     *
     * @param field the field
     * @param msg   the msg
     * @return the string
     */
    public static String parameterErrorFormat(String field, String msg) {
        return "【" + field + " : " + msg + "】";
    }
}
