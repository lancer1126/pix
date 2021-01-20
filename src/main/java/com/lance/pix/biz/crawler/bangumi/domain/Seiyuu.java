package com.lance.pix.biz.crawler.bangumi.domain;

import lombok.Data;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description 声优实体类
 */
@Data
public class Seiyuu {
    private Integer id;
    private String name;
    private String translatedName;
    private String avatar;
}