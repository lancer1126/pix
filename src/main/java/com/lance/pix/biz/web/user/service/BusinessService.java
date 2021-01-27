package com.lance.pix.biz.web.user.service;

import com.google.common.collect.Lists;
import com.lance.pix.biz.recommend.service.RecommendBizService;
import com.lance.pix.biz.userinfo.dto.ArtistWithIsFollowedInfo;
import com.lance.pix.biz.web.artist.service.ArtistBizService;
import com.lance.pix.biz.web.common.exception.BusinessException;
import com.lance.pix.biz.web.illust.service.IllustrationBizService;
import com.lance.pix.biz.web.user.dto.ArtistWithRecentlyIllusts;
import com.lance.pix.biz.web.user.mapper.BusinessMapper;
import com.lance.pix.common.constant.AuthConstant;
import com.lance.pix.common.constant.RedisKeyConstant;
import com.lance.pix.common.context.AppContext;
import com.lance.pix.common.po.Artist;
import com.lance.pix.common.po.Illustration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description BusinessService
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BusinessService {
    private final BusinessMapper businessMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final RecommendBizService recommendBizService;
    private final IllustrationBizService illustrationBizService;
    private final ArtistBizService artistBizService;

    /**
     * 关注某个画师
     */
    @Caching(evict = {
            @CacheEvict(value = "followedLatest", key = "#userId + 'illust'"),
            @CacheEvict(value = "followedLatest", key = "#userId + 'manga'"),
            @CacheEvict(value = "artist_followed", key = "#artistId+'1'+'30'")})
    @Transactional(rollbackFor = Exception.class)
    public void follow(int userId, int artistId, String username) {
        try {
            businessMapper.follow(userId, artistId, username, LocalDateTime.now());
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,"重复收藏");
        }
        stringRedisTemplate.opsForSet().add(RedisKeyConstant.ARTIST_FOLLOW_REDIS_PRE + artistId, String.valueOf(userId));
        recommendBizService.deleteFromRecommendationSet(userId, RedisKeyConstant.USER_RECOMMEND_ARTIST, artistId);
    }

    /**
     * 取消关注某个画师
     */
    @Caching(evict = {
            @CacheEvict(value = "followedLatest", key = "#userId + 'illust'"),
            @CacheEvict(value = "followedLatest", key = "#userId + 'manga'")})
    public void cancelFollow(int userId, int artistId) {
        int effectRow = businessMapper.cancelFollow(userId, artistId);
        stringRedisTemplate.opsForSet().remove(RedisKeyConstant.ARTIST_FOLLOW_REDIS_PRE + artistId, String.valueOf(userId));
        if (effectRow == 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "用户画师关系请求错误");
        }
    }

    /**
     * 收藏某个画作
     * @param userId        用户id
     * @param username      用户名
     * @param illustId      画作Id
     */
    public void bookmark(int userId, String username, int illustId) {
        bookmarkOperation(userId, username, illustId, 1, 0);
        recommendBizService.deleteFromRecommendationSet(userId, RedisKeyConstant.USER_RECOMMEND_BOOKMARK_ILLUST, illustId);
    }

    public void cancelBookmark(int userId, int illustId, int relationId) {
        bookmarkOperation(userId, null, illustId, -1, relationId);
    }

    /**
     * 收藏操作
     * @param userId        用户id
     * @param username      用户名
     * @param illustId      画作id
     * @param increment     判断是否已经标记了该画作
     * @param relationId    关系id
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "illust_bookmarked", key = "#illustId+'1'+'30'")
    public void bookmarkOperation(int userId, String username, int illustId, int increment, int relationId) {
        try {
            if (increment > 0) {
                stringRedisTemplate.opsForSet().add(RedisKeyConstant.BOOKMARK_REDIS_PRE + userId, String.valueOf(illustId));
                //异步往mysql中写入
                businessMapper.bookmark(userId, illustId, illustrationBizService.getIllustType(illustId), username, LocalDateTime.now());
            } else {
                stringRedisTemplate.opsForSet().remove(RedisKeyConstant.BOOKMARK_REDIS_PRE + userId, String.valueOf(illustId));
                businessMapper.cancelFollow(userId, illustId);
            }
            stringRedisTemplate.opsForSet().remove(RedisKeyConstant.BOOKMARK_COUNT_MAP_REDIS_PRE, String.valueOf(illustId), increment);
        } catch (Exception e) {
            if (increment > 0) {
                stringRedisTemplate.opsForSet().remove(RedisKeyConstant.BOOKMARK_REDIS_PRE + userId, String.valueOf(illustId));
            } else {
                stringRedisTemplate.opsForSet().add(RedisKeyConstant.BOOKMARK_REDIS_PRE + userId, String.valueOf(illustId));
            }
            throw new BusinessException(HttpStatus.BAD_REQUEST, "未知错误");
        }
    }

    /**
     * 列出所有收藏的画作
     * @param userId        用户id
     * @param type          illust或manga
     * @param currIndex     当前页数
     * @param pageSize      一页的大小
     */
    public List<Illustration> queryBookmark(int userId, String type, int currIndex, int pageSize) {
        Integer illustType;
        switch (type) {
            case "illust":
                illustType = 1;
                break;
            case "manga":
                illustType = 2;
                break;
            default:
                illustType = 3;
                break;
        }
        return illustrationBizService.queryIllustrationByIdList(businessMapper.queryBookmarked(userId, illustType, currIndex, pageSize));
    }

    /**
     * 查询用户关注的artist
     * @param userId        用户id
     * @param currIndex     当前目录索引
     * @param pageSize      一页大小
     * @return              artists
     */
    public List<Artist> queryFollowed(int userId, int currIndex, int pageSize) {
        List<Artist> artists = artistBizService.queryArtistsByIdList(businessMapper.queryFollowed(userId, currIndex, pageSize));
        if (artists.size() != 0) {
            if (AppContext.get() != null && AppContext.get().get(AuthConstant.USER_ID) != null) {
                int user = (int) AppContext.get().get(AuthConstant.USER_ID);
                List<Object> isFollowedList;
                if (user == userId) {
                    isFollowedList = artists.stream().map(e -> true).collect(Collectors.toList());
                } else {
                    isFollowedList = artists.stream().map(e -> stringRedisTemplate.opsForSet()
                            .isMember(RedisKeyConstant.ARTIST_FOLLOW_REDIS_PRE + e.getId(), String.valueOf(user))).collect(Collectors.toList());
                }
                for (int i = 0; i < artists.size(); i++) {
                    artistBizService.dealArtist(artists.get(i));
                    artists.set(i, new ArtistWithIsFollowedInfo(artists.get(i), (Boolean) isFollowedList.get(i)));
                }
            }
        }
        return artists;
    }

    /**
     * 获取关注的画师以及其最近的画作
     * @param userId        用户id
     * @param currIndex     当前索引
     * @param pageSize      一页大小
     * @return              画师以及画作
     */
    public List<ArtistWithRecentlyIllusts> queryFollowedWithRecentlyIllusts(Integer userId, int currIndex, int pageSize) {
        List<Artist> artists = queryFollowed(userId, currIndex, pageSize);
        return artists.stream().map(e -> {
            List<Illustration> illustrations = artistBizService.queryIllustrationsByArtistId(e.getId(), "illust", 0, 3);
            return new ArtistWithRecentlyIllusts(e, illustrations);
        }).collect(Collectors.toList());
    }

    public List<Illustration> queryFollowedLatest(int userId, String type, int page, int pageSize) {
        List<Integer> illustIdList = queryFollowedLatestSortedIllustId(userId, type).stream()
                .skip((long) pageSize * (page - 1)).limit(pageSize).collect(Collectors.toList());
        List<Illustration> illustrations = null;
        if (illustIdList.size() != 0) {
            illustrations = illustrationBizService.queryIllustrationByIllustIdList(illustIdList);
        }
        return illustrations;
    }

    @Cacheable(value = "followedLatest",key = "#userId+#type")
    public List<Integer> queryFollowedLatestSortedIllustId(int userId, String type) {
        //去除最近一个月关注画师的画作id
        List<Integer> illustrationIdList = businessMapper.queryFollowedLatestIllustId(userId, type);
        //遍历切割出k个升序数组,用大根堆进行合并得到TOP3000
        return mergekSortedArrays(split(illustrationIdList));

    }

    /**
     * 归并排序
     */
    public static List<Integer> mergekSortedArrays(List<List<Integer>> arrays) {
        ArrayList<Integer> list = new ArrayList<>();
        if (arrays == null || arrays.size() == 0 || arrays.get(0).size() == 0) {
            return list;
        }
        PriorityQueue<newInteger> pq = new PriorityQueue<>(arrays.size(), (x, y) -> x.value > y.value ? -1 : 1);
        for (int i = 0; i < arrays.size(); i++) {
            pq.offer(new newInteger(arrays.get(i).get(0), i, 0));
        }
        while (list.size() < 3000 && !pq.isEmpty()) {
            newInteger min = pq.poll();
            if (min.col + 1 < arrays.get(min.row).size()) {
                pq.offer(new newInteger(arrays.get(min.row).get(min.col + 1), min.row, min.col + 1));
            }
            list.add(min.value);
        }
        return list;
    }

    private static List<List<Integer>> split(List<Integer> illustrationIdList) {
        List<List<Integer>> result = new ArrayList<>();
        int size = illustrationIdList.size();
        if (size > 1) {
            int from = 0;
            int to = 1;
            for (; to < size; to++) {
                if (to == size - 1) {
                    result.add(Lists.reverse(illustrationIdList.subList(from, to + 1)));
                    break;
                } else if (to != size - 1 && illustrationIdList.get(to) > illustrationIdList.get(to + 1)) {
                    result.add(Lists.reverse(illustrationIdList.subList(from, to + 1)));
                    from = to + 1;
                }
            }
        }
        return result;
    }


    private static class newInteger {
        int value, row, col;

        public newInteger(int value, int row, int col) {
            this.value = value;
            this.row = row;
            this.col = col;
        }
    }

}















