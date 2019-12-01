package com.yx.common.web.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 返回结果类
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@NoArgsConstructor
@AllArgsConstructor
public class ResultModel<T> {

    public ResultModel(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 状态码
     */
    public int code;

    /**
     * 描述
     */
    public String message;

    /**
     * 数据结果集
     */
    public T data;

    /**
     * 分页结果记录数
     */
    public int count;
}
