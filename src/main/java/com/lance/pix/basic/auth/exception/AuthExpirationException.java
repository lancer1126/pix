package com.lance.pix.basic.auth.exception;

import com.lance.pix.common.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @Author lancer1126
 * @Date 2020-12-3
 * @Description token失效异常
 */
@Data
@AllArgsConstructor
public class AuthExpirationException extends BaseException {
    private HttpStatus httpStatus;
    private String message;
}