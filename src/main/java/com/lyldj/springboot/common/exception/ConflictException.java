package com.lyldj.springboot.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**     
  * @description: 未找到
  * @author: cqdzdj
  * @date: 2017/12/11 23:38
  */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Not Found")
public class ConflictException extends ServiceException {
}
