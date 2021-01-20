package com.lance.pix.basic.auth.annotation;

import com.lance.pix.basic.auth.constant.PermissionLevel;

import java.lang.annotation.*;

/**
 * @Author lancer1126
 * @Date 2020-12-3 21:07
 * @Description 自定义权限认证注解
 */
@Target({ElementType.ANNOTATION_TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionRequired {

    /**
     * 默认为0级权限（即需要登录）,设置时应使用AuthLevel的常量属性
     */
    int value() default PermissionLevel.LOGGED;
}