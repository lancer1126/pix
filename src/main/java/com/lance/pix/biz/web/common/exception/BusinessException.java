package com.lance.pix.biz.web.common.exception;

import com.lance.pix.common.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description Business异常类
 */
@Data
@AllArgsConstructor
public class BusinessException extends BaseException {
    private HttpStatus httpStatus;
    private String message;
}