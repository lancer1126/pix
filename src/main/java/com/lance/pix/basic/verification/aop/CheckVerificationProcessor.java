package com.lance.pix.basic.verification.aop;

import com.lance.pix.basic.verification.exception.VerificationCheckException;
import com.lance.pix.common.util.aop.JoinPointArgUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author lancer1126
 * @Date 2020-12-9
 * @Description aop权限校验处理
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheckVerificationProcessor {
    private final JoinPointArgUtil commonUtil;
    private final StringRedisTemplate stringRedisTemplate;
    private final String verificationCodeRedisPre = "v:";

    @Pointcut("@annotation(com.lance.pix.basic.verification.annotation.CheckVerification)")
    public void check() {

    }

    public void checkVerification(JoinPoint joinPoint) throws IllegalAccessException,NoSuchMethodException, InvocationTargetException {
        String value = commonUtil.getFirstMethodArgByAnnotationValueMethodValue(joinPoint, RequestParam.class, "value");
        String vid = commonUtil.getFirstMethodArgByAnnotationValueMethodValue(joinPoint, RequestParam.class, "vid");
        String v = stringRedisTemplate.opsForValue().get(verificationCodeRedisPre + vid);
        if (v == null) {
            throw new VerificationCheckException(HttpStatus.NOT_FOUND, "验证码不存在");
        }

        //在数据库中查询
        if (!value.equalsIgnoreCase(v)) {
            throw new VerificationCheckException(HttpStatus.BAD_REQUEST, "验证码错误");
        }
        stringRedisTemplate.delete(verificationCodeRedisPre + vid);
    }


















}



