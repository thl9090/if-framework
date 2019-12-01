package com.yx.common.core.exception;

/**
 * 业务异常类
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = -3662507630846977621L;

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public BusinessException() { super();
    }

    public BusinessException(Throwable ex) {
        super(ex);
    }

    public BusinessException(String message) {
        super(message);
        this.msg = message;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public BusinessException(int code, String message, Throwable ex) {
        super(message, ex);
        this.code = code;
        this.msg = message;
    }

}
