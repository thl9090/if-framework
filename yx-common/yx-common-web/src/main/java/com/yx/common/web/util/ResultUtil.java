package com.yx.common.web.util;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.Constants;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.web.model.ResultModel;

/**
 * 返回结果工具类
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public class ResultUtil {

    public static ResultModel ok() {
        return ok(null);
    }

    public static ResultModel ok(Object object) {
        if (object instanceof Page) {
            Page pageModel = (Page) object;
            return new ResultModel(Constants.ResultCodeEnum.SUCCESS.value(), Constants.ResultCodeEnum.SUCCESS.getMessage(), pageModel.getRecords(), pageModel.getTotal());
        }
        return new ResultModel(Constants.ResultCodeEnum.SUCCESS.value(), Constants.ResultCodeEnum.SUCCESS.getMessage(), object);
    }

    public static ResultModel fail(Constants.ResultCodeEnum resultCodeEnum) {
        return new ResultModel(resultCodeEnum.value(), resultCodeEnum.getMessage(), null);
    }

    public static ResultModel fail(int code, String message) {
        return new ResultModel(code, message, null);
    }

    public static ResultModel fail(Exception e) {
        if (e.getClass().equals(BusinessException.class)) {
            BusinessException businessException = (BusinessException) e;
            return new ResultModel(businessException.getCode(), businessException.getMsg(), null);
        }
        return fail();
    }

    public static ResultModel fail(Constants.ResultCodeEnum resultCodeEnum, String message) {
        return new ResultModel(resultCodeEnum.value(), StrUtil.isBlank(message) ? resultCodeEnum.getMessage() : message, null);
    }

    public static ResultModel fail() {
        return new ResultModel(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR.value(), Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR.getMessage(), null);
    }

    public static ResultModel fail(String msg) {
        return new ResultModel(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR.value(), msg, null);
    }
}
