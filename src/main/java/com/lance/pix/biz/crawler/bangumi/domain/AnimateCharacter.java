package com.lance.pix.biz.crawler.bangumi.domain;

import lombok.Data;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description AnimateCharacter实体类
 */
@Data
public class AnimateCharacter {
    private Integer id;
    private String avatar;
    private String name;
    private String translatedName;
    private String detail;
    private String trype;
    private Seiyuu seiyuu;
}