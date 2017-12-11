package com.lyldj.springboot.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**     
  * @description: 请求参数错误
  * @author: cqdzdj
  * @date: 2017/12/11 23:37
  */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad Request")
public class BadRequestException extends ServiceException {
}
