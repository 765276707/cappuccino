package com.github.xzb617.cappuccino.http;

import com.github.xzb617.cappuccino.commons.api.ClientApi;
import com.github.xzb617.cappuccino.commons.data.Server;
import com.github.xzb617.cappuccino.http.entity.RpcResponse;
import com.github.xzb617.cappuccino.http.ex.RpcException;
import com.github.xzb617.cappuccino.http.utils.HttpClientUtil;
import com.github.xzb617.cappuccino.http.utils.ResponseConverter;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * 基于HttpClient的请求代理者
 */
public class HttpClientRpcSender implements RpcSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpClientRpcSender.class);

    private final static int RETRY_FAIL_COUNT = 3;
    private final static int DEFAULT_TIMEOUT = 100000;
    private final static String DEFAULT_ENCODE = "UTF-8";

    private Integer timeout;
    private String encode;
    private Header[] headers;

    @Override
    public String getName() {
        return "httpClientRpcBroker";
    }

    @Override
    public void initConfig(Map<String, String> headers, Integer timeout, String encode) {
        this.headers = HttpClientUtil.buildHeaders(headers);
        this.timeout = timeout==null?DEFAULT_TIMEOUT:timeout;
        this.encode = encode==null?DEFAULT_ENCODE:encode;
    }

    @Override
    public RpcResponse get(Server server, String methodPath, Map<String, String> parameters) throws RpcException {
        // 构建API地址
        String url = server.getAddress() + methodPath;
        // 使用连接池
        HttpClient httpClient = HttpClientUtil.getInstance();
        HttpGet httpGet = new HttpGet();
        httpGet.setConfig(HttpClientUtil.buildRequestConfig(this.timeout, this.timeout));
        String strParameters = URLEncodedUtils.format(HttpClientUtil.buildNameValuePair(parameters), this.encode);
        httpGet.setURI(URI.create(url + "?" + strParameters));
        httpGet.setHeaders(this.headers);
        // 执行请求，并包装响应结果
        try {
            HttpResponse response = httpClient.execute(httpGet);
            return ResponseConverter.analysisAndConvert(response, this.encode);
        }
        catch (IOException e) {
            throw new RpcException("Failed to send get request to config server， cause by: " + e.getMessage());
        }
        finally {
            // 释放连接
            httpGet.releaseConnection();
        }
    }

    @Override
    public RpcResponse post(Server server, String methodPath, Map<String, String> parameters) throws RpcException {
        // 构建API地址
        String url = server.getAddress() + methodPath;
        // 使用连接池
        HttpClient httpClient = HttpClientUtil.getInstance();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = HttpClientUtil.buildRequestConfig(this.timeout, this.timeout);
        httpPost.setConfig(requestConfig);
        // 添加请求头
        httpPost.setHeaders(this.headers);
        List<NameValuePair> nameValuePairs = HttpClientUtil.buildNameValuePair(parameters);
        try {
            UrlEncodedFormEntity formParameters = new UrlEncodedFormEntity(nameValuePairs, this.encode);
            httpPost.setEntity(formParameters);
            HttpResponse response = httpClient.execute(httpPost);
            return ResponseConverter.analysisAndConvert(response, this.encode);
        }
        catch (IOException e) {
            throw new RpcException("Failed to send post request to config server, cause by: " + e.getMessage());
        }
        finally {
            httpPost.releaseConnection();
        }
    }

    @Override
    public RpcResponse delete(Server server, String methodPath, Map<String, String> parameters) throws RpcException {
        // 构建API地址
        String url = server.getAddress() + methodPath;
        // 使用连接池
        HttpClient httpClient = HttpClientUtil.getInstance();
        HttpDelete httpDelete = new HttpDelete();
        httpDelete.setConfig(HttpClientUtil.buildRequestConfig(this.timeout, this.timeout));
        String strParameters = URLEncodedUtils.format(HttpClientUtil.buildNameValuePair(parameters), this.encode);
        httpDelete.setURI(URI.create(url + "?" + strParameters));
        httpDelete.setHeaders(this.headers);
        // 执行请求，并包装响应结果
        try {
            HttpResponse response = httpClient.execute(httpDelete);
            return ResponseConverter.analysisAndConvert(response, this.encode);
        }
        catch (IOException e) {
            throw new RpcException("Failed to send delete request to config server， cause by: " + e.getMessage());
        }
        finally {
            // 释放连接
            httpDelete.releaseConnection();
        }
    }

    @Override
    public void ping(Server server, Map<String, String> parameters) {
        try {
            RpcResponse resp = this.get(server, ClientApi.PING, parameters);
            if (ResponseConverter.analysisPingResult(resp)) {
                if (!server.isAlive()) {
                    if (LOGGER.isWarnEnabled()) {
                        LOGGER.info("Unavailable service node [{}] ping successfully again and has been set to available status", server.getAddress());
                    }
                    server.setAlive(true);
                    // 重置失败计数器
                    server.resetConnectFailedCounter();
                }
            } else {
                // 计算失败
                this.handleFailedPing(server);
            }
        } catch (Throwable e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Unable to connect to the server normally, may be due to " + e.getMessage(), e);
            }
            // 计算失败
            this.handleFailedPing(server);
        }
    }


    /**
     * ping或连接失败时更新服务节点数据
     * @param server 服务节点
     */
    private void handleFailedPing(Server server) {
        // 只有当服务节点状态为可用时（UP），才需要进行失败计数操作
        if (server.isAlive()) {
            // 次数先取出后加1
            int failConnectCount = server.getConnectFailedCounter().getAndIncrement();
            if (failConnectCount >= RETRY_FAIL_COUNT) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn("Service node [{}] ping failed for more than 3 times and has been set to unavailable status", server.getAddress());
                }
                server.setAlive(false);
                server.resetConnectFailedCounter();
            }
        }
    }

}
