package com.jaaaain.exception;

/**
 * 业务异常
 */
public class BizException extends RuntimeException {

    private BizExceptionEnum bizExceptionEnum;

    public BizException() {
    }
    public BizException(String msg) {
        super(msg);
    }

    public BizException(BizExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.bizExceptionEnum = exceptionEnum;
    }

}