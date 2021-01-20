package com.lance.pix.biz.web.illust.po;

import com.lance.pix.common.po.Illustration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lancer1126
 * @Date 2020-12-6
 * @Description Rank
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rank implements Serializable {
    private List<Illustration> data;
    private String mode;
    private String date;
}