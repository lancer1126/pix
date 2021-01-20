package com.lance.pix.biz.web.illust.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description SearchSuggestion
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchSuggestion {
    private String keyword;
    private String keywordTranslated;

    public SearchSuggestion(String keyword) {
        this.keyword = keyword;
    }
}