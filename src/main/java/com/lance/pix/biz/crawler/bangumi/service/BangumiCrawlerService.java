package com.lance.pix.biz.crawler.bangumi.service;

import com.lance.pix.biz.crawler.bangumi.secmapper.AnimateMapper;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description bangumi爬虫service
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BangumiCrawlerService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final AnimateMapper animateMapper;
}