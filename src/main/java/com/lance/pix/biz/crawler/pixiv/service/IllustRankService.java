package com.lance.pix.biz.crawler.pixiv.service;

import com.lance.pix.biz.web.illust.po.Rank;
import com.lance.pix.common.po.Illustration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @Author lancer1126
 * @Date 2021-1-21
 * @Description TODO
 */
public class IllustRankService {
    private final static String[] MODES = {"day", "week", "month", "day_female", "day_male", "day_manga", "week_manga",
                                           "month_manga", "week_rookie_manga"};

    public void pullAllRank() {

    }

    public void pullAllRank(String date) {

    }

    private Rank getIllustrations(String mode, String date) {
        ArrayList<Illustration> illustrations = new ArrayList<>(100);
        IntStream.range(0,22).forEach(i -> {
            illustrations.addAll(getIllustrationsJson(mode, date, i));
        });
        return null;
    }

    private List<Illustration> getIllustrationsJson(String mode, String date, Integer index) {
        return null;
    }
}