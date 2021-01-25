package com.lance.pix.common.util.pixiv;

import com.lance.pix.biz.crawler.pixiv.domain.PixivUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author lancer1126
 * @Date 2021-1-21
 * @Description OAuth
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OauthManager {
    private volatile int pixivUserSize;
    @Getter
    private volatile List<PixivUser> pixivUserList;
    public PixivUser getRandomPixivUser() {
        long start = System.currentTimeMillis();
        //自旋获取
        while (System.currentTimeMillis() - start < 1000 * 15) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int i = random.nextInt(pixivUserSize);
            PixivUser pixivUser = pixivUserList.get(i);
            //token不为空且令牌桶足够
            if (pixivUser.getAccessToken() != null && pixivUser.getBucket().tryConsumeAndReturnRemaining(1).isConsumed()) {
                return pixivUser;
            }
        }
        //超时手动刷新
        //refreshAccessToken();
        throw new RuntimeException("获取token失败，开始重新刷新");
    }
}