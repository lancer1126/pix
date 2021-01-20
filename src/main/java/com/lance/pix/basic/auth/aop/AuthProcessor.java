package com.lance.pix.basic.auth.aop;

import com.lance.pix.basic.auth.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author lancer1126
 * @Date 2020-12-4
 * @Description aop权限校验处理
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Order(0)
public class AuthProcessor {
    private final JWTUtil jwtUtil;

}