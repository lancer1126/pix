package com.lance.pix.basic.verification.domain;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description 邮箱绑定验证码
 */
public class EmailBindingVerificationCode extends AbstractVerificationCode {
    private String email;

    public EmailBindingVerificationCode(String value, String email) {
        super(value);
        this.value = value;
        this.email = email;
    }

    @Override
    public String toString() {
        return value + email;
    }
}