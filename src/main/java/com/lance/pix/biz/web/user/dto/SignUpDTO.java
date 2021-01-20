package com.lance.pix.biz.web.user.dto;

import com.lance.pix.basic.sensitive.annotation.SensitiveCheck;
import com.lance.pix.biz.web.common.po.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Author lancer1126
 * @Date 2020-12-9
 * @Description 登录DTO
 */
@Data
public class SignUpDTO {
    @NotBlank
    @SensitiveCheck
    @Size(min = 2, max = 40)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    public User castToUser() {
        return new User(username, email, password);
    }
}