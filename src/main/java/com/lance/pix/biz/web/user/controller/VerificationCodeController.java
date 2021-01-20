package com.lance.pix.biz.web.user.controller;

import com.lance.pix.basic.verification.domain.ImageVerificationCode;
import com.lance.pix.biz.web.user.service.VerificationCodeService;
import com.lance.pix.common.po.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lancer1126
 * @Date 2020-12-7
 * @Description 验证码controller
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VerificationCodeController {
    private final VerificationCodeService verificationCodeService;

    @GetMapping("/verificationCode")
    public ResponseEntity<Result<ImageVerificationCode>> getImageVerificationCode() {
        ImageVerificationCode imageVerificationCode = verificationCodeService.getImageVerificationCode();
        return ResponseEntity.ok().body(new Result<>("验证码获取成功", imageVerificationCode));
    }
}