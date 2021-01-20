package com.lance.pix.common.po.illust;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description Tag
 */
@Data
@NoArgsConstructor
public class Tag {
    protected String name;
    protected String translatedName;
    private Long id;

    public Tag(String name, String translatedName) {
        this.name = name;
        this.translatedName = translatedName;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public String getTranslatedName() {
        return translatedName == null ? "" : translatedName;
    }
}