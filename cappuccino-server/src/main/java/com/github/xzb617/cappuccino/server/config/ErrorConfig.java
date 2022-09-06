package com.github.xzb617.cappuccino.server.config;

import com.github.xzb617.cappuccino.server.base.AjaxResponse;
import com.github.xzb617.cappuccino.server.base.ErrorResponse;
import com.github.xzb617.cappuccino.server.base.ErrorStatus;
import com.github.xzb617.cappuccino.server.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ValidationException;
import java.io.IOException;
import java.util.List;

/**
 * 全局异常捕获，最高优先级
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorConfig {

    // 日志
    private final Logger log = LoggerFactory.getLogger(ErrorConfig.class);


    /**
     * 业务异常
     * @param e ServiceException
     * @return
     */
    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<AjaxResponse> handleServiceException(ServiceException e) {
        return ResponseEntity.ok(AjaxResponse.failure(e.getMessage()));
    }


    /**
     * 参数错误
     * @HttpStatus 400
     * @param e
     * @return
     */
    @ExceptionHandler(value={
            ValidationException.class,
            BindException.class,
            MissingServletRequestParameterException.class,
            MissingPathVariableException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ErrorResponse> handleParametersException(Exception e) {
        // 获取错误消息
        String errorMessage = "非法参数，请求已被拒绝";
        if (e instanceof ValidationException
                || e instanceof MissingServletRequestParameterException
                || e instanceof MissingPathVariableException) {
            errorMessage = e.getMessage();
        }
        else if (e instanceof MethodArgumentNotValidException) {
            errorMessage = ValidationUtil.getParameterErrorMessage((MethodArgumentNotValidException) e, errorMessage);
        }
        else if (e instanceof BindException) {
            errorMessage = ValidationUtil.getParameterErrorMessage((BindException) e, errorMessage);
        }
        else if (e instanceof HttpMessageNotReadableException
                || e instanceof MethodArgumentTypeMismatchException) {
            errorMessage = "参数类型或格式错误";
        }
        // 响应
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ErrorStatus.ILLEGAL_REQUEST_PARAMETER, errorMessage));
    }



    /**
     * 其它错误
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleServerException(Exception e) {
        if (e instanceof IOException) {
            // 远程主机强迫关闭了一个现有的连接
            log.warn("远程主机强迫关闭了一个现有的连接");
        }
        e.printStackTrace();
        System.out.println(e.getMessage());
        // 响应
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ErrorStatus.INTERNAL_SERVER_ERROR));
    }


    /**
     * 参数校验错误信息工具类
     * @author Pristine Xu
     */
    private static class ValidationUtil {
        /**
         * 获取参数错误信息
         * @param e
         * @param defaultMessage
         * @return
         */
        private static String getParameterErrorMessage(MethodArgumentNotValidException e, String defaultMessage) {
            BindingResult bindingResult = e.getBindingResult();
//            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//            if (!CollectionUtils.isEmpty(fieldErrors)) {
//                FieldError fieldError = fieldErrors.get(0);
//                return fieldError.getDefaultMessage();
//            }
            List<ObjectError> objectErrors = bindingResult.getAllErrors();
            if (!CollectionUtils.isEmpty(objectErrors)) {
                return objectErrors.get(0).getDefaultMessage();
            }
            return defaultMessage;
        }

        /**
         * 获取参数错误信息
         * @param e
         * @param defaultMessage
         * @return
         */
        private static String getParameterErrorMessage(BindException e, String defaultMessage) {
            BindingResult bindingResult = e.getBindingResult();
//            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//            if (!CollectionUtils.isEmpty(fieldErrors)) {
//                return fieldErrors.get(0).getDefaultMessage();
//            }
            List<ObjectError> objectErrors = bindingResult.getAllErrors();
            if (!CollectionUtils.isEmpty(objectErrors)) {
                return objectErrors.get(0).getDefaultMessage();
            }
            return defaultMessage;
        }
    }

}
