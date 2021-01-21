package com.lance.pix.biz.web.illust.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lance.pix.biz.web.illust.po.Rank;
import com.lance.pix.biz.web.illust.secmapper.RankMapper;
import com.lance.pix.common.po.Illustration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lancer1126
 * @Date 2020-12-6
 * @Description 首页排行
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(propagation = Propagation.NOT_SUPPORTED, transactionManager = "SecondaryTransactionManager")
public class RankService {
    private final RankMapper rankMapper;
    private final ObjectMapper objectMapper;

    @Cacheable(value = "rank")
    public List<Illustration> queryByDateAndMode(String date, String mode, int page, int pageSize) {
        List<Illustration> illustrationList = new ArrayList<>();
        Rank rank = rankMapper.queryByDateAndMode(date, mode);
        if (rank != null) {
            illustrationList = rank.getData().stream().skip((long) pageSize * (page - 1)).limit(pageSize).collect(Collectors.toList());
        }
        return objectMapper.convertValue(illustrationList, new TypeReference<List<Illustration>>() {});
    }
}