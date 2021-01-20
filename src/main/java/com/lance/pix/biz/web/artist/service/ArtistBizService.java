package com.lance.pix.biz.web.artist.service;

import com.lance.pix.biz.web.artist.secmapper.ArtistBizMapper;
import com.lance.pix.biz.web.illust.service.IllustrationBizService;
import com.lance.pix.common.constant.RedisKeyConstant;
import com.lance.pix.common.po.Artist;
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
 * @Date 2020-12-6
 * @Description artist相关service
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArtistBizService {
    private final StringRedisTemplate stringRedisTemplate;
    private final ArtistBizMapper artistBizMapper;
    private LinkedBlockingDeque<Integer> waitForPullArtistInfoQueue;
    private LinkedBlockingDeque<String> waitForPullArtistQueue;
    private IllustrationBizService illustrationBizService;


    /**
     * 根据artist的Id列表获取一定数量的artists
     * @param artistIdList  artistId列表
     * @return              artists
     */
    public List<Artist> queryArtistsByIdList(List<Integer> artistIdList) {
        return artistIdList.stream().parallel().map(this::queryArtistByIdFromDb).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 根据Id获取某个artist
     * @param artistId      artistId
     * @return              artist
     */
    @Cacheable(value = "artist")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Artist queryArtistByIdFromDb(Integer artistId) {
        if (stringRedisTemplate.opsForSet().isMember(RedisKeyConstant.BLOCK_ARTISTS_SET, String.valueOf(artistId))) {
            return null;
        }
        Artist artist = artistBizMapper.queryArtistById(artistId);
        if (artist == null) {
            waitForPullArtistInfoQueue.offer(artistId);
        }
        return artist;
    }

    /**
     * 设置某个artist的关注数
     * @param artist    目标artist
     */
    public void dealArtist(Artist artist) {
        artist.setTotalFollowUsers(String.valueOf(stringRedisTemplate.opsForSet().size(RedisKeyConstant.ARTIST_FOLLOW_REDIS_PRE + artist.getId())));
    }

    /**
     * 根据artistId获取illusts
     * @param artistId      画师id
     * @param type          illust或maga
     * @param currIndex     目前索引页
     * @param pageSize      一页大小
     * @return              返回该画师的插画
     */
    @Cacheable(value = "artist_illusts")
    public List<Illustration> queryIllustrationsByArtistId(Integer artistId, String type, int currIndex, int pageSize) {
        String key = artistId + ":" + type;
        if (currIndex == 0 && pageSize == 30) {
            waitForPullArtistQueue.offer(key);
        }
        List<Integer> illustrations = artistBizMapper.queryIllustrationsByArtistId(artistId, type, currIndex, pageSize);
        return illustrationBizService.queryIllustrationByIdList(illustrations);
    }
}









