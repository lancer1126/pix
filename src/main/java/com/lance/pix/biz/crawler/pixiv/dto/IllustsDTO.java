package com.lance.pix.biz.crawler.pixiv.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author lancer1126
 * @Date 2021-1-26
 * @Description IllustsDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IllustsDTO {
    private List<IllustrationDTO> illusts;
    @JsonSetter("next_url")
    private String nextUrl;
}