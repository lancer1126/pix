package com.lance.pix.biz.web.user.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description 收藏画作实体类
 */
@Data
@NoArgsConstructor
public class BookmarkRelation {
    private int id;
    @NotNull
    private String username;
    @NotNull
    private int userId;
    @NotNull
    private int illustId;
}