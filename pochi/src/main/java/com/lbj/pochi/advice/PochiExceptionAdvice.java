package com.lbj.pochi.advice;

import com.lbj.pochi.exception.PochiException;
import com.lbj.pochi.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 定义统一异常处理
 *
 */
@ControllerAdvice
@Slf4j
public class PochiExceptionAdvice {

    /**
     * 统一处理 BlogException
     *
     * @param exception
     */
    @ExceptionHandler(PochiException.class)
    @ResponseBody
    public Result<Object> exceptionHandler(PochiException exception) {
        log.error("统一异常处理：", exception);
        return new Result<>(exception.getErrorCode(), exception.getMessage());
    }
}

