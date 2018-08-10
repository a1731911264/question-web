package com.manji.edu.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static Map<String, Object> result(int status, String message, Object result) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", message);
        map.put("result",result);
        return map;
    }

    public  static Map<String, Object> success(String message, Object result) {
        return result(200, message, result);
    }

    public  static Map<String, Object> error(String message) {
        return result(500, message, new HashMap());
    }

    public static Map<String, Object> resourceNotFound(String message) {
        return result(404, message, new HashMap<>());
    }

    public static void resourceUnauthorized(HttpServletResponse response) {
        Map<String, Object> result = result(401, "认证失效，请重新登陆！", new HashMap<>());

        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        try {
            response.getWriter().write(JSONObject.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
