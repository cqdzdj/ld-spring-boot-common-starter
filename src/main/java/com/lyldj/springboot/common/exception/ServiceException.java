package com.lyldj.springboot.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 服务（业务）异常
 */
public class ServiceException extends RuntimeException {

    private HttpStatus statusCode;
    private int errorCode;
    private String message;


    public ServiceException(){
        this(ExceptionType.INTERNAL_BASE);
    }

    public ServiceException(ExceptionType exceptionType) {
        this.statusCode = exceptionType.getStatusCode();
        this.errorCode = exceptionType.getErrorCode();
        this.message = exceptionType.getMessage();
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
