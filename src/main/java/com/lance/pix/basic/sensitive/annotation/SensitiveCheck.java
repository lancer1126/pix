package com.lance.pix.basic.sensitive.annotation;

import java.lang.annotation.*;

/**
 * @Author lancer1126
 * @Date 2020-12-9
 * @Description 铭感词校验
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SensitiveCheck {
}