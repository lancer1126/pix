package com.lance.pix.biz.crawler.pixiv.domain;

import com.lance.pix.common.util.pixiv.OathResp;
import com.lance.pix.common.util.pixiv.OathRespBody;
import com.lance.pix.common.util.pixiv.RequestUtil;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.Data;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
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

    public String getRequestBody() {
        Map<String, String> paramsMap = new HashMap<>(
                Map.of("client_id", CLIENT_ID,
                        "client_secret",CLIENT_SECRET,
                        "grant_type", grantType,
                        "username", username,
                        "password", password,
                        "device_token", deviceToken,
                        "refresh_token", refreshToken,
                        "get_secure_url", "true"));
        return RequestUtil.getPostEntity(paramsMap);
    }

    public String getAccessToken() {
        readLock.lock();
        try {
            return accessToken;
        } finally {
            readLock.unlock();
        }
    }

    public boolean refresh(OathRespBody oathRespBody) {
        writeLock.lock();
        try {
            OathResp resp = oathRespBody.getResponse();
            if (resp != null) {
                accessToken = resp.getAccessToken();
                deviceToken = resp.getDeviceToken();
                refreshToken = resp.getRefreshToken();
                grantType = "refresh_token";
                isBan = 0;
                return true;
            }
        } finally {
            writeLock.unlock();
        }
        return false;
    }

    public void ban() {
        writeLock.lock();
        try {
            accessToken = null;
            grantType = "password";
            isBan = 1;
        } finally {
            writeLock.unlock();
        }
    }
}