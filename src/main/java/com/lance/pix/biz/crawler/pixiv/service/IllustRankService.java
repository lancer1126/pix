package com.lance.pix.biz.crawler.pixiv.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lance.pix.biz.crawler.pixiv.dto.IllustrationDTO;
import com.lance.pix.biz.crawler.pixiv.dto.IllustsDTO;
import com.lance.pix.biz.web.illust.po.Rank;
import com.lance.pix.common.po.Illustration;
import com.lance.pix.common.util.pixiv.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author lancer1126
 * @Date 2021-1-21
 * @Description TODO
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IllustRankService {
    private final static String[] MODES = {"day", "week", "month", "day_female", "day_male", "day_manga", "week_manga",
                                           "month_manga", "week_rookie_manga"};
    private final RequestUtil requestUtil;
    private final ObjectMapper objectMapper;

    public void pullAllRank() {

    }

    public void pullAllRank(String date) {

    }

    private Rank getIllustrations(String mode, String date) {
        ArrayList<Illustration> illustrations = new ArrayList<>(100);
        IntStream.range(0,22).forEach(i -> {
            try {
                illustrations.addAll(getIllustrationsJson(mode, date, i));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                System.out.println("重试");
                try {
                    illustrations.addAll(getIllustrationsJson(mode, date, i));
                } catch (ExecutionException | InterruptedException executionException) {
                    executionException.printStackTrace();
                }
            }
        });
        String rankMode;
        switch (mode) {
            case "day_female":
                rankMode = "female";
                break;
            case "day_male":
                rankMode = "male";
                break;
            default:
                rankMode = mode;
                break;
        }
        return new Rank(illustrations, rankMode, date);
    }

    private List<Illustration> getIllustrationsJson(String mode, String date, Integer index)
            throws ExecutionException, InterruptedException {
        return requestUtil.getJson("http://proxy.pixivic.com:23334/v1/illust/ranking?mode=" + mode + "&offset=" + index * 30 + "&date=" + date)
                .thenApply(result -> {
                    if ("false".equals(result)) {
                        return null;
                    }
                    try {
                        IllustsDTO rankDTO = objectMapper.readValue(result, new TypeReference<IllustsDTO>() {
                        });
                        return rankDTO.getIllusts()
                                .stream()
                                .filter(Objects::nonNull)
                                .map(IllustrationDTO::castToIllustration)
                                .collect(Collectors.toList());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).get();
    }
}











