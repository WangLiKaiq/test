package com.macro.mall.common.api;

/**
 * @author komorebi
 * @since 2022-04-14
 * 常用API返回对象
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    /**
     * All processes is OK.
     */
    SUCCESS(200, "OK"),
    /**
     * Failure by unknown reason.
     */
    GENERIC_FAILED(500, "Failure by unknown reason."),
    /**
     * Parameters can't match.
     */
    PARAMETER_NO_MATCH(404, "Parameters can't match."),
    /**
     * Token expires.
     */
    TOKEN_EXPIRE(401, "Token expires."),
    /**
     *
     */
    NO_LOGIN(402, "User isn't login."),
    /**
     * No Authority.
     */
    NO_AUTHORITY(403, "No Authority.");
    /**
     *
     */
    private final long code;
    /**
     *
     */
    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
