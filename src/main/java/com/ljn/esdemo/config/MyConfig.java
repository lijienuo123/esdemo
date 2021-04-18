package com.ljn.esdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Configuration
public class MyConfig {/*

    //超时时间设为5分钟
    private static final int TIME_OUT = 5 * 60 * 1000;
    private static final int ADDRESS_LENGTH = 2;
    private static final String HTTP_SCHEME = "http";

    private final int connectTimeOut = 1000; // 连接超时时间
    private final int socketTimeOut = 30000; // 连接超时时间
    private final int connectionRequestTimeOut = 500; // 获取连接的超时时间

    private final int maxConnectNum = 100; // 最大连接数
    private final int maxConnectPerRoute = 100; // 最大路由连接数
    //权限验证
    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

    //    @Value("${elasticsearch.address}")
    private final String[] address = {"localhost:9200"};

    @Bean(name = "restHighLevelClient")
    public RestHighLevelClient restHighLevelClient() {
        HttpHost[] hosts = Arrays.stream(address)
                .map(this::makeHttpHost)
                .filter(Objects::nonNull)
                .toArray(HttpHost[]::new);
        log.debug("hosts:{}", Arrays.toString(hosts));
        System.out.println("hosts:{}" + Arrays.toString(hosts));
        //配置权限验证
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        return new RestHighLevelClient(RestClient.builder(hosts)

                .setRequestConfigCallback(
                        requestConfigBuilder ->
                                requestConfigBuilder
                                        .setConnectTimeout(connectTimeOut)
                                        .setSocketTimeout(socketTimeOut)
                                        .setConnectionRequestTimeout(connectionRequestTimeOut))

                .setHttpClientConfigCallback(
                        httpClientBuilder ->
                                httpClientBuilder
                                        .setDefaultCredentialsProvider(credentialsProvider)
                                        .setMaxConnTotal(maxConnectNum)
                                        .setMaxConnPerRoute(maxConnectPerRoute))

                .setRequestConfigCallback(
                        requestConfigBuilder ->
                                requestConfigBuilder.setSocketTimeout(TIME_OUT)));

    }

    *//**
     * 处理请求地址
     *
     * @param s address
     * @return HttpHost
     *//*
    private HttpHost makeHttpHost(String s) {
        assert !Objects.isNull(s);
        String[] address = s.split(":");
        if (address.length == ADDRESS_LENGTH) {
            String ip = address[0];
            int port = Integer.parseInt(address[1]);
            return new HttpHost(ip, port, HTTP_SCHEME);
        } else {
            return null;
        }
    }*/
}