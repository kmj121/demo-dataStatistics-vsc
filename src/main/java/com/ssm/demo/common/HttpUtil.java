package com.ssm.demo.common;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/5/6
 */
public class HttpUtil {
    public static HttpClient httpClient;

    public static HttpClient getHttpClient() {
        return httpClient != null ? httpClient : new HttpClient();
    }

    public static String post(String url, Map<String, String> params) throws IOException {
        Set<String> keys = params.keySet();

        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        for (String key : keys) {
            postMethod.setParameter(key, params.get(key));
        }

        getHttpClient().executeMethod(postMethod);
        return postMethod.getResponseBodyAsString();

    }

    public static String doGet(String url) throws Exception {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);

        if(null == url || !url.startsWith("http")) {
            throw new Exception("请求地址格式不对");
        }
        //设置请求的编码方式
        method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

        int statusCode = client.executeMethod(method);

        //如果返回状态值不是200，则打印服务器返回的状态
        if(statusCode != HttpStatus.SC_OK) {
            System.out.println("Method failed: " + method.getStatusLine());
        }

        //返回响应信息
        byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
        //在返回响应信息使用编码（utf-8或gb2312）
        String response = new String(responseBody, "utf-8");
        System.out.println("===================response:" + response);
        //释放连接
        method.releaseConnection();
        return response;
    }
}
