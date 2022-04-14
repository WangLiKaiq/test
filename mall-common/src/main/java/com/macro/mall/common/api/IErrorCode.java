package com.macro.mall.common.api;

/**
 * @author komorebi
 * @since 2022-04-14
 * 常用API返回对象接口
 */
public interface IErrorCode {
    /**
     * Get error code.
     *
     * @return error code
     */
    long getCode();

    /**
     * Get error message.
     * @return error message
     */
    String getMessage();
}
