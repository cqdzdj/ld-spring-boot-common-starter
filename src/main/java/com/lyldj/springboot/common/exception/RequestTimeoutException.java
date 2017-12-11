package com.lyldj.springboot.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
  * @description: 请求超时
  * @author: cqdzdj
  * @date: 2017/12/11 23:37
  */
@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT, reason = "Bad Request")
public class RequestTimeoutException extends ServiceException {
}
