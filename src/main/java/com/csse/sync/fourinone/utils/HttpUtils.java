package com.csse.sync.fourinone.utils;

import com.alibaba.fastjson.JSONObject;
import com.csse.sync.fourinone.po.CsseUser;
import com.sun.deploy.net.HttpResponse;
//import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static String doGet(String url){
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);
        // 响应模型
        CloseableHttpResponse response = null;
        org.apache.http.HttpEntity responseEntity = null;
        String result= null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                result = EntityUtils.toString(responseEntity);
                System.out.println("响应内容为:" + result);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            releasingResources(httpClient,response);
        }
        return result;
    }

    public static String doPost(String url, Map<String, Object> map){
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 参数
        StringBuffer urlAndParams = new StringBuffer();
        // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
        urlAndParams.append(url + "?");
        map.forEach((key, value) -> {
                urlAndParams.append(key + "=" + value+ "&");
        });
        // 创建Post请求
        HttpPost httpPost = new HttpPost(urlAndParams.toString());

        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        org.apache.http.HttpEntity responseEntity= null;
        // 响应模型
        CloseableHttpResponse response = null;
        String result= null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                //System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
                result = EntityUtils.toString(responseEntity);
                System.out.println("响应内容为:" +result);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            releasingResources(httpClient,response);
        }
        return result;
    }
    public static String doPost(String urlAddr, String params, String contentType) throws Exception {
        long begin = System.currentTimeMillis();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(contentType));
        // headers.set("Content-Type","application/json;charset=UTF-8");
        //headers.set("slw.jwt.token",token);
        //restTemplate
        HttpEntity<String> request = new HttpEntity<>(params, headers);
        // String res = restTemplate.postForObject(urlAddr,request,String.class);
        ResponseEntity<String> response = restTemplate.postForEntity(urlAddr,request,String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        return response.getBody();

    }
    public static String doGet(String urlAddr, String paramsMap,String contentType) throws Exception {
        long begin = System.currentTimeMillis();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.valueOf(contentType));


        HttpEntity<String> request = new HttpEntity<>(paramsMap,headers);
        ResponseEntity<String> response = restTemplate.getForEntity(urlAddr,String.class,request);

        System.out.println(response.getBody());

        return response.getBody();
    }

    private static void releasingResources( CloseableHttpClient httpClient, CloseableHttpResponse response){
        try {
            // 释放资源
            if (httpClient != null) {
                httpClient.close();
            }
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String doPut(String url, Map<String, String> headerMap,String requestBodyJson) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String entityStr = null;
        CloseableHttpResponse response = null;
        try {
            HttpPut post = new HttpPut(url);
            //添加头部信息
            for (Map.Entry<String, String> header : headerMap.entrySet()) {
                post.addHeader(header.getKey(), header.getValue());
            }
            org.apache.http.HttpEntity entity = new StringEntity(requestBodyJson,"Utf-8");
            System.out.println("请求体是："+requestBodyJson);
            post.setEntity(entity);
            response = httpClient.execute(post);
            // 获得响应的实体对象
            org.apache.http.HttpEntity httpEntity = response.getEntity();
            // 使用Apache提供的工具类进行转换成字符串
            entityStr = EntityUtils.toString(httpEntity, "UTF-8");
            System.out.println("PUT请求路径："+post);
            System.out.println("PUT请求结果："+entityStr);
        } catch (ClientProtocolException e) {
            System.err.println("Http协议出现问题");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("解析错误");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO异常");
            e.printStackTrace();
        }
        return entityStr;
    }

    public static String  httpDelete(String url,Map<String,String> headers,String encode){
       // HttpResponse response = new HttpResponse();
        if(encode == null){
            encode = "utf-8";
        }
        String content = null;
        //since 4.3 不再使用 DefaultHttpClient
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpDelete httpdelete = new HttpDelete(url);
        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpdelete.setHeader(entry.getKey(),entry.getValue());
            }
        }
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpdelete);
            org.apache.http.HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
//            response.setBody(content);
//            response.setHeaders(httpResponse.getAllHeaders());
//            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
//            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {   //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void main(String[] args) throws Exception {
        //http post
//        Map map = new HashMap();
//        map.put("username", "sysadmin");
//        map.put("password", "password02!");
//        map.put("loginMode", "100");
//        doPost("http://192.168.42.11:10005/api/sso/authen",map);

        //http get
        // doGet("http://192.168.42.11:10005/api/org/department/-1/subdepartments?access_token=e48bf7d1-9d6d-4460-b37f-101e641309ea&sublevel");

        //http put
//        Map<String, String> map = new HashMap<>();
//        map.put("Content-Type", "application/json");
//        CsseUser csseUser = new CsseUser();
//        csseUser.setUserid("3e83e85d-617b-466b-a545-963bcd3f585c");
//        csseUser.setFullname("zhangsan");
//        csseUser.setIsManager(4);
//        csseUser.setOrganId("e980d8e3-30df-4d73-baa3-f8d35e174f20");
//        csseUser.setSecLevel("3");
//        csseUser.setSex("1");
//        List<String> list = new ArrayList<>();
//        list.add("e980d8e3-30df-4d73-baa3-f8d35e174f20");
//        csseUser.setOrganIds(list);
//        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(csseUser);
//
//        System.out.println(jsonObject);
//        doPut("http://192.168.42.11:10005/api/org/userinfo/3e83e85d-617b-466b-a545-963bcd3f585c?access_token=a1758c58-7c54-4d0a-854a-60f91a202f87",map,jsonObject.toJSONString());



        //http delete
//        String url ="http://192.168.42.11:10005/api/org/userinfo/3e83e85d-617b-466b-a545-963bcd3f585c?access_token=a1758c58-7c54-4d0a-854a-60f91a202f87";
//        Map<String, String> map = new HashMap<>();
//        map.put("Content-Type", "application/json");
//        String result = httpDelete(url,map,null);
//        System.out.println(result);

        //doPost("http://192.168.42.11:10005/api/org/department?access_token=4f153aad-cae0-4582-9d0a-257bd79a6a0a","{\"code\": \"123123133\",\"fatherId\": \"root\",\"organName\": \"XXXX室\",\"organId\":\"123123133\",\"isTemporary\": 0}","application/json;charset=utf8");

        /*Map<String,String> haderMap = new HashMap<String,String>();
        haderMap.put("Content-Type","application/json;charset=utf8");
        doPut("http://192.168.42.11:10005/api/org/department/123123133?access_token=4f153aad-cae0-4582-9d0a-257bd79a6a0a",haderMap,"{\"code\": \"123123133\",\"fatherId\": \"222\",\"organName\": \"XXx室\",\"organId\":\"123123133\",\"isTemporary\": 0}");*/
       // doGet("http://192.168.42.11:10005/api/org/userinfo/5376387f-c12c-4eb0-a465-7b750d7dbbac?access_token=4f153aad-cae0-4582-9d0a-257bd79a6a0a","","application/json;charset=utf8");

        String timeStr1= LocalDateTime.now().plusMinutes(-2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String timeStr2= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("当前时间为:"+timeStr1);
        System.out.println("当前时间为-2:"+timeStr2);
    }


}
