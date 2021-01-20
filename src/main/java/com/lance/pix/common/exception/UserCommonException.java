package com.lance.pix.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @Author lancer1126
 * @Date 2020-12-9
 * @Description UserCommonException
 */
@Data
@AllArgsConstructor
public class UserCommonException extends BaseException{
    private HttpStatus httpStatus;
    private String message;
}