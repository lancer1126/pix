package com.lance.pix.biz.crawler.pixiv.domain;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.Data;

import java.time.Duration;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author lancer1126
 * @Date 2021-1-21
 * @Description Pixiv账户信息
 */
@Data
public class PixivUser {
    public final static String CLIENT_ID = "";
    public final static String CLIENT_SECRET = "";
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock(false);
    final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    private volatile String accessToken;
    private String deviceToken;
    private String refreshToken;
    private String grantType;
    private String username;
    private String password;
    private Bucket bucket;
    private volatile Integer isBan;

    {
        deviceToken = "";
        refreshToken = "";
        grantType = "password";
        bucket = Bucket4j.builder()
                .addLimit(Bandwidth.classic(800, Refill.intervally(800, Duration.ofMinutes(10)))).build();
    }
}