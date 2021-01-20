package com.lance.pix.basic.verification.util;

import com.lance.pix.basic.verification.constant.VerificationType;
import com.lance.pix.basic.verification.domain.AbstractVerificationCode;
import com.lance.pix.basic.verification.domain.EmailBindingVerificationCode;
import com.lance.pix.basic.verification.domain.ImageVerificationCode;

import java.util.Random;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description 验证码构造器
 */
public class VerificationCodeBuildUtil {
    private final static Random random;
    private final static String str;

    static {
        random = new Random();
        str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    }

    private String type;
    private String value;
    private String email;

    private VerificationCodeBuildUtil(String type) {
        this.type = type;
    }

    private static String generateRamdomStr(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        int len = str.length() - 1;
        double r;
        for (int i = 0; i < n; i++) {
            r = (Math.random()) * len;
            stringBuilder.append(str.charAt((int) r));
        }
        return stringBuilder.toString();
    }

    public static VerificationCodeBuildUtil create(String type) {
        return new VerificationCodeBuildUtil(type);
    }

    public VerificationCodeBuildUtil type(String type) {
        this.type = type;
        return this;
    }

    public VerificationCodeBuildUtil value(String value) {
        this.value = value;
        return this;
    }

    public VerificationCodeBuildUtil email(String email) {
        this.email = email;
        return this;
    }

    public AbstractVerificationCode build() throws RuntimeException {
        if (type == null) {
            throw new RuntimeException("未指定类型");
        }
        if (value == null) {
            value = generateRamdomStr(4);
        }
        switch (type) {
            case VerificationType.IMG:
                return new ImageVerificationCode(value);
            case VerificationType.EMAIL_CHECK:
                return new EmailBindingVerificationCode(value, email);
            default:
                return null;
        }
    }
}















