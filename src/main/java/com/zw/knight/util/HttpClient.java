package com.zw.knight.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * HttpClient
 *
 * @author zw
 * @date 2020/7/20
 */
public class HttpClient {
    public static String get(String url, Map<String, String> params) {
        url += "?";
        StringBuilder urlBuilder = new StringBuilder(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        url = urlBuilder.toString();
        return HttpClient.get(url);
    }

    public static String get(String url) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        HttpHost proxy = new HttpHost("192.168.7.24", 9998);
        CloseableHttpClient httpClient = HttpClientBuilder.create()
//                .setProxy(proxy)
                .build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);

        // 响应模型
        try {
            return getString(httpClient, httpClient.execute(httpGet));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String post(String url, String params) {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
//                .setProxy(proxy)
                .build();
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(params, "UTF-8");
        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        // 响应模型
        try {
            return getString(httpClient, httpClient.execute(httpPost));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getString(CloseableHttpClient httpClient, CloseableHttpResponse execute) {
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = execute;
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                return EntityUtils.toString(responseEntity);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                httpClient.close();
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
