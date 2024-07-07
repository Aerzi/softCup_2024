package com.example.backend.base;

import lombok.Getter;

/**
 * @version 1.0
 * @description The type Web mvc configuration.
 * @author feixia0g
 * @date 2024/7/7 9:40
 */
@Getter
public enum SystemCode {
    /**
     * OK
     */
    OK(200, "请求成功"),
    /**
     * AccessTokenError
     */
    AccessTokenError(400, "用户登录令牌失效"),
    /**
     * UNAUTHORIZED
     */
    UNAUTHORIZED(401, "用户未登录"),
    /**
     * UNAUTHORIZED
     */
    AuthError(402, "用户名或密码错误"),
    /**
     * InnerError
     */
    InnerError(500, "系统内部错误"),
    /**
     * ParameterValidError
     */
    ParameterValidError(501, "参数验证错误"),

    /**
     * AccessDenied
     */
    AccessDenied(502, "用户没有权限访问");

    /**
     * The Code.
     * -- GETTER --
     *  Gets code.
     *
     * @return the code

     */
    int code;
    /**
     * The Message.
     * -- GETTER --
     *  Gets message.
     *
     * @return the message

     */
    String message;

    SystemCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
