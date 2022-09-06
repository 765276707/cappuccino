package com.github.xzb617.cappuccino.http.utils;

import com.github.xzb617.cappuccino.commons.exception.client.ClientAuthFailedException;
import com.github.xzb617.cappuccino.http.entity.RpcResponse;
import com.github.xzb617.cappuccino.http.ex.RpcException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ResponseConverter {

    public static RpcResponse analysisAndConvert(HttpResponse httpResponse, String encode) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 401) {
            // 客户端认证失败
            throw new ClientAuthFailedException();
        }
        HttpEntity entity = httpResponse.getEntity();
        if (statusCode==204 || statusCode==304) {
            return new RpcResponse(statusCode, null);
        }

        if (statusCode != 200) {
            String errorMsg = "请求到配置中心出错, 错误码：" + statusCode;
            if (entity != null) {
                errorMsg = EntityUtils.toString(entity, encode);
            }
            throw new RpcException(errorMsg);
        }
        return new RpcResponse(statusCode, EntityUtils.toString(entity, encode));
    }


    public static boolean analysisPingResult(RpcResponse rpcResponse) {
        return rpcResponse.getHttpStatus()==200
                && "pong".equalsIgnoreCase(rpcResponse.getHttpEntity());
    }
}
