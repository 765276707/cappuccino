package com.github.xzb617.cappuccino.http.utils;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class HttpClientUtil {

    /**
     * 日志
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    /**
     * 请求获取数据的超时时间，单位毫秒。如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用
     */
    private final static int SOCKET_TIMEOUT = 10000;
    /**
     * 设置连接超时时间，单位毫秒
     */
    private final static int CONNECT_TIMEOUT = 10000;
    /**
     * 池化管理器
     */
    private static PoolingHttpClientConnectionManager connectionManager = null;
    /**
     * 连接池
     */
    private static CloseableHttpClient closeableHttpClient;

    static {
        HttpClientBuilder builder = HttpClients.custom();
        builder.disableCookieManagement();
        builder.disableRedirectHandling();
        // 禁用默认的自动重试机制
        builder.disableAutomaticRetries();

        // 支持http和https
        Registry factoryRegistry = RegistryBuilder.create()
                .register("https", PlainConnectionSocketFactory.getSocketFactory())
                .register("http", new PlainConnectionSocketFactory())
                .build();

        // 路由配置
        connectionManager = new PoolingHttpClientConnectionManager(factoryRegistry);
        connectionManager.setMaxTotal(200); //最大连接数
        connectionManager.setDefaultMaxPerRoute(10);//每个路由最大连接数，总连接数应小于 MaxTotal
        connectionManager.setValidateAfterInactivity(1000); //官方推荐使用这个来检查永久链接的可用性，而不推荐每次请求的时候才去检查（ms），-1表示关闭此功能


        closeableHttpClient = builder
                .setConnectionManager(connectionManager)
                .setConnectionManagerShared(true)
                .evictIdleConnections(1, TimeUnit.MINUTES) //开启独立线程定期回收空闲连接
                .evictExpiredConnections() //开启独立线程清理过期连接
                .setDefaultRequestConfig(
                        RequestConfig
                                .custom()
                                .setAuthenticationEnabled(false)
                                .setCircularRedirectsAllowed(false)
                                .setSocketTimeout(SOCKET_TIMEOUT)
                                .setConnectTimeout(CONNECT_TIMEOUT)
                                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                                .build()
                )
                .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE) // 连接重用策略 是否能keepAlive
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)// 长连接配置，即获取长连接生产多长时间
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))// 重试次数 默认3次 此处禁用
                .build();


        // 程序退出时(JVM钩子)，关闭 CloseableHttpClient
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                closeableHttpClient.close();
                LOGGER.info("CloseableHttpClient closed before application shutdown.");
            } catch (IOException e) {
                LOGGER.error("CloseableHttpClient close error", e);
            }
        }));
    }

    /**
     * 获取连接池
     * @return
     */
    public static CloseableHttpClient getInstance() {
        return closeableHttpClient;
    }


    /**
     * 构建请求配置对象
     * @param socketTimeout socket超时时间
     * @param connectTimeout connect超时时间
     * @return RequestConfig
     */
    public static RequestConfig buildRequestConfig(int socketTimeout, int connectTimeout) {
        // 设置请求和传输超时时间
        return RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .build();
    }

    /**
     * 将请求参数转换为NameValuePair列表
     * @param parameterMap 请求参数
     * @return List<NameValuePair>
     */
    public static List<NameValuePair> buildNameValuePair(Map<String, String> parameterMap) {
        List<NameValuePair> parameters = null;
        if (parameterMap != null) {
            Set<Map.Entry<String, String>> vars = parameterMap.entrySet();
            parameters = new ArrayList<>(vars.size());
            for (Map.Entry<String, String> entry : vars) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        } else {
            parameters = new ArrayList<>(0);
        }
        return parameters;
    }

    /**
     * 构建请求头数组
     * @param headers 请求头参数
     * @return Header[]
     */
    public static Header[] buildHeaders(Map<String, String> headers) {
        Set<Map.Entry<String, String>> entries = headers.entrySet();
        List<Header> headerList = new ArrayList<>(entries.size());
        for (Map.Entry<String, String> entry : entries) {
            headerList.add(new BasicHeader(entry.getKey(), entry.getValue()));
        }
        Header[] headerArr = new BasicHeader[headerList.size()];
        return headerList.toArray(headerArr);
    }

}
