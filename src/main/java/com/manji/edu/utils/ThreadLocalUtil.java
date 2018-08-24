package com.manji.edu.utils;

public class ThreadLocalUtil {

    private static ThreadLocal<String> userIdThreadLocal= new ThreadLocal<>();

    private static ThreadLocal<String> tokenThreadLocal = new ThreadLocal<>();

    public static String getUserId() {
        return userIdThreadLocal.get();
    }

    public static void setUserId(String userId) {
        userIdThreadLocal.set(userId);
    }

    public static String getToken() {
        return tokenThreadLocal.get();
    }

    public static void setToken(String token) {
        tokenThreadLocal.set(token);
    }
}
