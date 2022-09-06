package com.github.xzb617.cappuccino.server.utils;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Pristine Xu
 * @date 2022/2/3 3:19
 * @description:
 */
public class ResponseUtil {

    /**
     * 响应数据
     * @param response
     * @param data
     * @throws IOException
     */
    public static <T> void writeJSON(HttpServletResponse response,
                                     HttpStatus status,
                                     @NonNull String data) throws IOException {
        // 设置响应类型
        if (status != null) {
            response.setStatus(status.value());
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        // 输出
        PrintWriter pw = response.getWriter();
        pw.write(data);
        pw.flush();
        pw.close();
    }

}
