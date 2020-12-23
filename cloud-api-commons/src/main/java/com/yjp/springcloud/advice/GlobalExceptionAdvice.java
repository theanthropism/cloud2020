package com.yjp.springcloud.advice;

import com.yjp.springcloud.entities.CommonResult;
import com.yjp.springcloud.exception.BizException;
import com.yjp.springcloud.exception.TipException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;


/**
 * 全局异常拦截，主要拦截验证信息
 *
 * @since 2019/3/2
 */
@Slf4j
@ControllerAdvice
@Order(2019)
public class GlobalExceptionAdvice {

    /**
     * 参数校验失败异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public CommonResult handleBindException(BindException e) {
        return buildErrorResponse(e, e.getBindingResult());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResult handleBindException(MethodArgumentNotValidException e) {
        return buildErrorResponse(e, e.getBindingResult());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public CommonResult handleConstraintViolation(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.stream().findFirst().get();
        return CommonResult.error(violation.getMessage());
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public CommonResult handleHttpMethodException(HttpRequestMethodNotSupportedException e) {
        String msg = String.format("不支持 [%s] 请求", e.getMethod());
        return CommonResult.error(msg);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public CommonResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String msg = String.format("缺少 %s 参数", e.getParameterName());
        return CommonResult.error(msg);
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public CommonResult handleBizException(BizException e) {
        log.error("业务异常", e);
        return CommonResult.error(e.getMessage());
    }

    @ExceptionHandler(TipException.class)
    @ResponseBody
    public CommonResult handleTipException(TipException e) {
        return CommonResult.error(e.getMessage());
    }

    /**
     * 全局异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return CommonResult.error("请求失败");
    }

    private CommonResult<String> buildErrorResponse(Exception e, BindingResult bindingResult) {
        if (null != bindingResult && bindingResult.hasFieldErrors() &&
                !StringUtils.contains(bindingResult.getFieldError().getDefaultMessage(), "Exception")) {
            return CommonResult.error(bindingResult.getFieldError().getDefaultMessage());
        }
        log.warn("参数校验异常: ", e);
        return CommonResult.error("参数校验失败");
    }

}
