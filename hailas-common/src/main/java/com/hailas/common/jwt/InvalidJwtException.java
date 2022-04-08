package com.hailas.common.jwt;

/**
 * 无效的jwt
 * @author wulin
 * @version 1.0.0
 * @created 2019/8/23.
 */
public class InvalidJwtException extends Exception {
    public InvalidJwtException(String message) {
        super(message);
    }
}
