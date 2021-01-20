package com.lance.pix.biz.notify.po;

import com.lance.pix.biz.web.common.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description Actor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actor {
    private Integer userId;
    private String username;

    public static Actor castFromUser(User user) {
        return new Actor(user.getId(), user.getUsername());
    }
}