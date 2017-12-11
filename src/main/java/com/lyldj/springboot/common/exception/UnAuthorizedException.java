package com.lyldj.springboot.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**     
  * @description: 未认证异常
  * @author: cqdzdj
  * @date: 2017/12/11 23:54
  */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
public class UnAuthorizedException extends ServiceException {
}
