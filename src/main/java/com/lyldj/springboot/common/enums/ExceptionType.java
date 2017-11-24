package com.lyldj.springboot.common.enums;

import org.springframework.http.HttpStatus;

/**
 * @author: duanjian
 * @date: 17-11-21 上午10:18
 * @description: 异常类型
 */
public enum ExceptionType {

    // 400 exception
    /**
     * base 400 exception
     */
    BAD_BASE(HttpStatus.BAD_REQUEST, 0, "Incomplete request"),
    /**
     * parameter exception
     */
    BAD_PARAM(HttpStatus.BAD_REQUEST, 1, "请求参数不正确"),
    /**
     * parameter expired exception
     */
    BAD_EXPIRED(HttpStatus.BAD_REQUEST, 2, "请求参数失效"),
    /**
     * captcha exception
     */
    BAD_CAPTCHA(HttpStatus.BAD_REQUEST, 3, "验证码不正确"),
    /**
     * captcha expired exception
     */
    BAD_CAPTCHA_EXPIRED(HttpStatus.BAD_REQUEST, 4, "验证码失效"),
    /**
     * other 400 exception
     */
    BAD_OTHER(HttpStatus.BAD_REQUEST, 99, "无效请求"),

    // 401 exception
    /**
     * base 401 exception
     */
    UNAUTHORIZED_BASE(HttpStatus.UNAUTHORIZED, 0, "Unauthorized in application"),
    /**
     * no user
     */
    UNAUTHORIZED_NO_USER(HttpStatus.UNAUTHORIZED, 1, "无效用户"),
    /**
     * wrong user or password
     */
    UNAUTHORIZED_WRONG_USER_PW(HttpStatus.UNAUTHORIZED, 2, "用户名或密码错误"),
    /**
     * not verified
     */
    UNAUTHORIZED_NO_VERIFY(HttpStatus.UNAUTHORIZED, 3, "未认证"),
    /**
     * verify fail
     */
    UNAUTHORIZED_FAIL_VERIFY(HttpStatus.UNAUTHORIZED, 4, "认证失败"),
    /**
     * token expire
     */
    UNAUTHORIZED_TOKEN_EXPIRE(HttpStatus.UNAUTHORIZED, 5, "token失效"),
    /**
     * token not found
     */
    UNAUTHORIZED_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, 6, "token不存在"),
    /**
     * token validate fail
     */
    UNAUTHORIZED_TOKEN_VALIDATE_FAIL(HttpStatus.UNAUTHORIZED, 7, "token校验失败"),
    /**
     * token unavailable
     */
    UNAUTHORIZED_TOKEN_UNAVAILABLE(HttpStatus.UNAUTHORIZED, 8, "token不可用"),
    /**
     * other 401 exception
     */
    UNAUTHORIZED_OTHER(HttpStatus.UNAUTHORIZED, 99, "认证失败"),

    // 403 exception
    /**
     * base 403 exception
     */
    FORBIDDEN_BASE(HttpStatus.FORBIDDEN, 0, "application forbidden"),

    /**
     * no right
     */
    FORBIDDEN_NO_RIGHT(HttpStatus.FORBIDDEN, 1, "没有权限"),
    /**
     * other 403 exception
     */
    FORBIDDEN_OTHER(HttpStatus.FORBIDDEN, 99, "禁止访问"),

    // 404 exception
    /**
     * base 404 exception
     */
    NOT_FOUND_BASE(HttpStatus.NOT_FOUND, 0, "Not Found in application"),
    /**
     * unavailable link
     */
    NOT_FOUND_LINK_UNAVAILABLE(HttpStatus.NOT_FOUND, 1, "无效的链接"),
    /**
     * no image
     */
    NOT_FOUND_IMAGE(HttpStatus.NOT_FOUND, 2, "找不到图片"),
    /**
     * no file
     */
    NOT_FOUND_FILE(HttpStatus.NOT_FOUND, 3, "找不到文件"),
    /**
     * no resource
     */
    NOT_FOUND_RESOURCE(HttpStatus.NOT_FOUND, 4, "找不到资源"),
    /**
     * no user
     */
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 5, "找不到用户"),
    /**
     * no company
     */
    NOT_FOUND_COMPANY(HttpStatus.NOT_FOUND, 6, "找不到公司"),
    /**
     * other 404 exception
     */
    NOT_FOUND_OTHER(HttpStatus.NOT_FOUND, 99, "资源未找到"),

    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, 0, "Request Timeout"),

    // 409 exception
    /**
     * base 409 exception
     */
    CONFLICT_BASE(HttpStatus.CONFLICT, 0, "Conflict in application"),
    /**
     * phone conflict exception
     */
    CONFLICT_PHONE(HttpStatus.CONFLICT, 1, "手机号已被使用"),
    /**
     * email conflict exception
     */
    CONFLICT_EMAIL(HttpStatus.CONFLICT, 2, "邮箱已被使用"),
    /**
     * name conflict exception
     */
    CONFLICT_NAME(HttpStatus.CONFLICT, 3, "用户名已被使用"),
    /**
     * other 409 exception
     */
    CONFLICT_OTHER(HttpStatus.CONFLICT, 99, "冲突啦"),

    // 500 exception
    /**
     * base 500 exception
     */
    INTERNAL_BASE(HttpStatus.INTERNAL_SERVER_ERROR, 0, "Internal Exception in application"),
    /**
     * encrypt fail
     */
    INTERNAL_ENCRYPT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, 1, "加密失败"),
    /**
     * decrypt fail
     */
    INTERNAL_DECRYPT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, 2, "解密失败"),
    /**
     * no sha1 algorithm
     */
    INTERNAL_NO_SHA1_ALGORITHM(HttpStatus.INTERNAL_SERVER_ERROR, 3, "SHA-1算法不存在"),
    /**
     * no md5 algorithm
     */
    INTERNAL_NO_MD5_ALGORITHM(HttpStatus.INTERNAL_SERVER_ERROR, 4, "MD5 算法不存在"),
    /**
     * other 500 exception
     */
    INTERNAL_OTHER(HttpStatus.INTERNAL_SERVER_ERROR, 99, "内部错误"),

    // 500 exception
    /**
     * base 503 exception
     */
    SERIVCE_BASE(HttpStatus.INTERNAL_SERVER_ERROR, 0, "Service Unavailable in application"),
    /**
     * other 503 exception
     */
    SERIVCE_OTHER(HttpStatus.INTERNAL_SERVER_ERROR, 99, "服务不可用");

    private final HttpStatus statusCode;
    private final int errorCode;
    private final String message;

    ExceptionType(HttpStatus statusCode, int errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
