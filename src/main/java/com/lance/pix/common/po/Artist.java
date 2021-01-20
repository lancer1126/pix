package com.lance.pix.common.po;

import lombok.Data;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description Artist实体类
 */
@Data
public class Artist {
    protected Integer id;
    protected String name;
    protected String account;
    protected String avatar;
    protected String comment;
    protected String gender;
    protected String birthDay;
    protected String region;
    protected String webPage;
    protected String twitterAccount;
    protected String twitterUrl;
    protected String totalFollowUsers;
    protected String totalIllustBookmarksPublic;
}