package com.yjp.springcloud.exception;

/**
 * 仅用于提示的异常，该异常不会导致输出堆栈
 *
 * @since 2019/3/7
 */
public class TipException extends RuntimeException {

    public TipException() {
    }

    public TipException(String message) {
        super(message);
    }

}
