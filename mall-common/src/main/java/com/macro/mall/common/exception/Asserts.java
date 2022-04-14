package com.macro.mall.common.exception;

import com.macro.mall.common.api.IErrorCode;

/**
 * @author komorebi
 * @since 2022-04-14
 * 断言处理类，用于抛出各种API异常
 */
public class Asserts {
    /**
     * Static method to get ApiException by message.
     *
     * @param message error message
     */
    public static void fail(String message) {
        throw new ApiException(message);
    }

    /**
     * Static method to get ApiException by errorCode.
     *
     * @param errorCode error code
     */
    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

}
