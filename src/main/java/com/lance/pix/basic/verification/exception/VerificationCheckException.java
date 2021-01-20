package com.lance.pix.basic.verification.exception;

import com.lance.pix.common.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description 验证码校验异常
 */
@Data
@AllArgsConstructor
public class VerificationCheckException extends BaseException {
    private HttpStatus httpStatus;
    private String message;
}