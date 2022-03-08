package com.example.pep.iot.emq.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pep.iot.emq.config.EmqAuthConfig;
import com.example.pep.iot.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * emq服务的http请求调用类
 *
 * @author LiuGang
 * @since 2022-02-17 1:55 PM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmqService {

    public static RequestConfig requestConfig;

    private final EmqAuthConfig emqAuthConfig;

    @PostConstruct
    private void init() {
        requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000).setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
    }

    /**
     * 通过http请求emq,添加client
     *
     * @param clientId clientId
     * @param password password
     */
    public Integer addClient(String clientId, String password) {
        String url = emqAuthConfig.getHttpUrl().concat(emqAuthConfig.getClientAuthUrl());
        HttpPost httpPost = new HttpPost(url);
        String param = new JSONObject()
                .fluentPut("clientid", clientId)
                .fluentPut("password", password).toJSONString();
        try {
            String result = this.sendPost(httpPost, param);
            JSONObject json = JSONObject.parseObject(result);
            Integer code = json.getInteger("code");
            if (Objects.nonNull(code) && code == 0) {
                return code;
            }
            throw new BizException(String.format("注册失败,响应结果是:%s", result));
        } catch (Exception e) {
            log.error("request emq auth error,param:{},url:{},message:{}", param, httpPost.getURI().getPath(), e.getMessage());
            throw new BizException("emq服务器连接失败");
        }
    }

    public List<String> getClients() {
        String url = emqAuthConfig.getHttpUrl().concat(emqAuthConfig.getClientAuthUrl());
        HttpGet httpGet = new HttpGet(url);
        try {
            String res = sendGet(httpGet);
            JSONObject json = JSONObject.parseObject(res);
            if (json.getInteger("code") == 0 && CollectionUtil.isNotEmpty(json.getJSONArray("data"))) {
                return json.getJSONArray("data").toJavaList(String.class);
            }
        } catch (IOException e) {
            log.error("get auth clients error,path:{}", url);
            throw new BizException("请求emq,网络错误");
        }
        return new ArrayList<>();
    }

    /**
     * 发送post请求
     */
    private String sendPost(HttpPost httpPost, String param) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setEntity(new StringEntity(param, StandardCharsets.UTF_8));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String res = EntityUtils.toString(entity);
        httpClient.close();
        return res;
    }


    /**
     * 发送get请求
     */
    private String sendGet(HttpGet httpGet) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        httpGet.setConfig(requestConfig);
        httpGet.addHeader("Content-type", "application/json; charset=utf-8");
        httpGet.setHeader("Accept", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String res = EntityUtils.toString(entity);
        httpClient.close();
        return res;


    }

    /**
     * 获取带有权限的HttpClient连接
     */
    private CloseableHttpClient getHttpClient() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                emqAuthConfig.getUsername(), emqAuthConfig.getPassword());
        provider.setCredentials(AuthScope.ANY, credentials);
        return HttpClients.custom().setDefaultCredentialsProvider(provider).build();
    }

}
