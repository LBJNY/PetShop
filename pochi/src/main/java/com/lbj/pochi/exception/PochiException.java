package com.lbj.pochi.exception;


import com.lbj.pochi.enums.ResultEnum;

/**
 * 自定义异常
 *
 * @date
 */
public class PochiException extends RuntimeException {

    private static final long serialVersionUID = 2450214686001409867L;

    private Integer errorCode = ResultEnum.ERROR.getCode();

    public PochiException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.errorCode = resultEnum.getCode();
    }

    public PochiException(ResultEnum resultEnum, Throwable throwable) {
        super(resultEnum.getMsg(), throwable);
        this.errorCode = resultEnum.getCode();
    }

    public PochiException(Integer errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public PochiException(String msg) {
        super(msg);
    }

    public PochiException(Throwable throwable) {
        super(throwable);
    }

    public PochiException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
