package com.lance.pix.biz.web.user.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description FollowedRelation
 */
@Data
@NoArgsConstructor
public class FollowedRelation {
    @NotNull
    private Integer userId;

    @NotNull
    private String username;

    @NotNull
    private Integer artistId;
}