package com.lance.pix.common.po.illust;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description ArtistPreView
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistPreView {
    protected Integer id;
    protected String name;
    protected String account;
    protected String avatar;
}