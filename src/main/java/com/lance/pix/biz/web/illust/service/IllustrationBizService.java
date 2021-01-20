package com.lance.pix.biz.web.illust.service;

import com.lance.pix.biz.web.illust.secmapper.IllustrationBizMapper;
import com.lance.pix.common.constant.RedisKeyConstant;
import com.lance.pix.common.po.Illustration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description IllustrationBizService
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IllustrationBizService {
    private final StringRedisTemplate stringRedisTemplate;
    private final IllustrationBizMapper illustrationBizMapper;
    private final LinkedBlockingDeque<Integer> waitForPullIllustQueue;

    public Integer getIllustType(Integer illustId) {
        return null;
    }

    /**
     * 根据插画id列表获取插画
     * @param illustId  插画id列表
     * @return          插画list
     */
    public List<Illustration> queryIllustrationByIdList(List<Integer> illustId) {
        return illustId.stream().parallel().map(this::queryIllustrationByIdFromDb).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 根据某个插画id获取插画
     * @param illustId  插画id
     * @return          插画
     */
    @Cacheable(value = "illust")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Illustration queryIllustrationByIdFromDb(Integer illustId) {
        //判断是否在封禁集合中
        if (stringRedisTemplate.opsForSet().isMember(RedisKeyConstant.BLOCK_ILLUSTS_SET, String.valueOf(illustId))) {
            return null;
        }
        Illustration illustration = illustrationBizMapper.queryIllustrationByIllustId(illustId);
        if (illustration == null) {
            log.info("画作" + illustId + "不存在，加入队列等待爬取");
            waitForPullIllustQueue.offer(illustId);
        }
        return illustration;
    }

    public List<Illustration> queryIllustrationByIllustIdList(List<Integer> illustIdList) {
        return illustIdList.stream().parallel().map(this::queryIllustrationByIdFromDb).filter(Objects::nonNull).collect(Collectors.toList());
    }
}















