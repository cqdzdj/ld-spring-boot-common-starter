package com.lyldj.springboot.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**     
  * @description: 请求受限
  * @author: cqdzdj
  * @date: 2017/12/11 23:38
  */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Forbidden")
public class ForbiddenException extends ServiceException {
}
