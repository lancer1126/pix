package com.lance.pix.biz.userinfo.annotation;

import java.lang.annotation.*;

/**
 * @Author lancer1126
 * @Date 2020-12-6
 * @Description 附带用户关注信息注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithUserInfo {
}