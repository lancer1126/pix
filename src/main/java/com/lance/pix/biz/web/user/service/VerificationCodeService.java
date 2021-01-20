package com.lance.pix.biz.web.user.service;

import com.lance.pix.basic.verification.constant.VerificationType;
import com.lance.pix.basic.verification.domain.EmailBindingVerificationCode;
import com.lance.pix.basic.verification.domain.ImageVerificationCode;
import com.lance.pix.basic.verification.util.VerificationCodeBuildUtil;
import com.lance.pix.common.constant.RedisKeyConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author lancer1126
 * @Date 2020-12-7
 * @Description VerificationCodeService
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VerificationCodeService {
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 获取图片验证码
     * @return  验证码
     */
    public ImageVerificationCode getImageVerificationCode() {
        ImageVerificationCode verificationCode = (ImageVerificationCode) VerificationCodeBuildUtil.create(VerificationType.IMG).build();
        stringRedisTemplate.opsForValue().set(RedisKeyConstant.VERIFICATION_CODE + verificationCode.getVid(), verificationCode.getValue(), 10, TimeUnit.MINUTES);
        return verificationCode;
    }

    /**
     * 获取邮箱验证
     * @param email     邮箱
     * @return          verify
     */
    public EmailBindingVerificationCode getEmailVerificationCode(String email) {
        EmailBindingVerificationCode verificationCode = (EmailBindingVerificationCode) VerificationCodeBuildUtil.create(VerificationType.EMAIL_CHECK)
                .email(email).build();
        stringRedisTemplate.opsForValue().set(RedisKeyConstant.VERIFICATION_CODE + verificationCode.getVid(), verificationCode.getValue(),3, TimeUnit.HOURS);
        return verificationCode;
    }
}














