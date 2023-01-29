package com.suke.czx.common.exception;

import com.suke.zhjg.common.autofull.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * 异常处理器
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2016年10月27日 下午10:16:19
 */
@Slf4j
@RestControllerAdvice
public class RRExceptionHandler extends R {
    /**
     * 自定义异常
     */
    @ExceptionHandler(RRException.class)
    public R handleRRException(RRException e) {
        return R.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R handleAccessDeniedException(AccessDeniedException e) {
        return R.error(HttpStatus.FORBIDDEN.value(), "没有权限，请联系管理员授权");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数错误:", e);
        return R.error("参数错误");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数JSON解析错误:", e);
        return R.error("参数JSON解析错误");
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error("发生异常", e);
        return R.error(e.getMessage());
    }
}
