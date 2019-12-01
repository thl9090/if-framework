package com.yx.common.web.handler;

import com.yx.common.core.Constants;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Slf4j
@RestControllerAdvice
public class PlatformExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(Exception.class)
    public ResultModel exceptionHandler(Exception e) {
        e.printStackTrace();
        log.info("异常拦截Exception信息：" + e.getMessage());
        // 媒体类型
        if (e instanceof HttpMediaTypeNotSupportedException) {
            return ResultUtil.fail(Constants.ResultCodeEnum.NO_SUPPORTED_MEDIATYPE);
        }
        // springboot参数验证框架如果验证失败则抛出MethodArgumentNotValidException异常
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, fieldError.getDefaultMessage());
        }
        // Shiro 安全校验失败
        if (e instanceof AuthorizationException) {
            return ResultUtil.fail(Constants.ResultCodeEnum.UNAUTHORIZED, Constants.ResultCodeEnum.UNAUTHORIZED.getMessage());
        }
        return ResultUtil.fail(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR, Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResultModel exceptionHandler(BusinessException e) {
        e.printStackTrace();
        log.info("异常拦截BusinessException信息：" + e.getMsg());
        return ResultUtil.fail(e.getCode() == 0 ? Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR.value() : e.getCode(), e.getMsg());
    }
}
