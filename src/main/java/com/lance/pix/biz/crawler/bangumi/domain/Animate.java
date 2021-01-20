package com.lance.pix.biz.crawler.bangumi.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description Animate实体类
 */
@Data
public class Animate {
    private Integer id;
    private String title;
    private String translatedTitle;
    private String type;
    private Map<String ,String> detail;
    private String intro;
    private List<String> tags;
    private String cover;
    private Float rate;
    private List<AnimateCharacter> characters;
}