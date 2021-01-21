package com.lance.pix.common.util.pixiv;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

/**
 * @Author lancer1126
 * @Date 2021-1-21
 * @Description RequestUtil
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RequestUtil {
    private final OauthManager oauthManager;

    public CompletableFuture<String> getJson(String url) {
        HttpRequest.Builder uri = HttpRequest.newBuilder().uri(URI.create(url));
        decorateHeader(uri);
        //todo
        return null;
    }

    public static void decorateHeader(HttpRequest.Builder builder) {
        String[] hash = getHash();
        builder.header("Artist-Agent", "PixivAndroidApp/5.0.93 (Android 5.1; m2)")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("App-OS", "android")
                .header("App-OS-Version", "5.1")
                .header("App-Version", "5.0.93")
                .header("Accept-Language", "zh_CN")
                .header("X-Client-Hash", hash[1])
                .header("X-Client-Time", hash[0]);
    }

    public static String[] getHash() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.US);
        String time = fmt.format(new Date());

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String seed = time + "28c1fdd170a5204386cb1313c7077b34f83e4aaf4aa829ce78c231e05b0bae2c";
        assert md5 != null;
        byte[] digest = md5.digest(seed.getBytes());
        StringBuilder hash = new StringBuilder();
        for (byte b : digest)
            hash.append(String.format("%02x", b));
        return new String[]{time, hash.toString()};
    }
}