package org.softbattle.klog_server.user.Handler;

import org.softbattle.klog_server.user.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 登录异常捕获类
 * @author ygx
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Result handlerRuntimeException(RuntimeException ex) {
        return Result.error();
    }

}
