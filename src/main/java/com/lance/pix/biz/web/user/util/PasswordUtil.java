package com.lance.pix.biz.web.user.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;

/**
 * @Author lancer1126
 * @Date 2020-12-9
 * @Description PasswordUtil
 */
@Component
public class PasswordUtil {
    public String encrypt(String password) {
        byte[] plainText = password.getBytes();
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.reset();
            md.update(plainText);
            byte[] encodedPassword = md.digest();
            for (byte b : encodedPassword) {
                if ((b & 0xff) < 0x10) {
                    sb.append("0");
                }
                sb.append(Long.toString(b & 0xff, 16));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}