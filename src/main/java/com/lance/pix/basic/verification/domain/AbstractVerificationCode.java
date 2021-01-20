package com.lance.pix.basic.verification.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description 验证码抽象类
 */
@NoArgsConstructor
public class AbstractVerificationCode {
    @Getter
    protected String vid;
    @Getter
    @JsonIgnore
    protected String value;

    protected AbstractVerificationCode(String value) {
        this.vid = UUID.randomUUID().toString();
        this.value = value;
    }
}