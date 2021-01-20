package com.lance.pix.biz.recommend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description 推荐相关service
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecommendBizService {
    private final StringRedisTemplate stringRedisTemplate;

    public void deleteFromRecommendationSet(Integer userId, String key, Integer targetId) {
        stringRedisTemplate.opsForSet().remove(key + userId, String.valueOf(targetId));
    }
}