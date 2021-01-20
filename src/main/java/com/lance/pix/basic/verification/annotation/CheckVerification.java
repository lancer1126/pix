package com.lance.pix.basic.verification.annotation;

import com.lance.pix.basic.verification.constant.VerificationType;

import java.lang.annotation.*;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description 验证码校验注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckVerification {

    String value() default VerificationType.IMG;
}