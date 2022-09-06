package com.github.xzb617.cappuccino.server.base;

import java.util.Objects;

/**
 * Ajax请求响应
 * @author xzb617
 * @param <T>
 */
public class AjaxResponse<T> {

    private Boolean success;

    private String message;

    private T data;

    private AjaxResponse(Boolean success) {
        this.success = success;
    }

    private AjaxResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    private AjaxResponse(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> AjaxResponse<T> success() {
        return new AjaxResponse<>(true);
    }

    public static <T> AjaxResponse<T> failure() {
        return new AjaxResponse<>(false);
    }

    public static <T> AjaxResponse<T> failure(String message) {
        return new AjaxResponse<T>(false, message);
    }

    public AjaxResponse<T> message(String message) {
        this.message = message;
        return this;
    }

    public AjaxResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AjaxResponse<?> that = (AjaxResponse<?>) o;
        return Objects.equals(success, that.success) &&
                Objects.equals(message, that.message) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, data);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

}
