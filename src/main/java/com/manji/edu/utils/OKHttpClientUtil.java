package com.manji.edu.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OKHttpClientUtil {

    public static String httpGet(String url) {
        String result = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送httppost请求
     *
     * @param url
     * @param data  提交的参数为key=value&key1=value1的形式
     * @return
     */
    public static String httpPost(String url, String data) {
        String result = null;
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/html;charset=utf-8"), data);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = httpClient.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String httpPostOfForm(String url, JSONObject param) {
        String result = null;
        OkHttpClient httpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        Set<Map.Entry<String, Object>> entries = param.entrySet();
        for (Map.Entry<String, Object> entry: entries) {
            formBody.add(entry.getKey(), (String) entry.getValue());
        }
        Request request = new Request.Builder().url(url).post(formBody.build()).build();
        try {
            Response response = httpClient.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
