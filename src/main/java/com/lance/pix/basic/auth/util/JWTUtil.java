package com.lance.pix.basic.auth.util;

import com.lance.pix.basic.auth.config.AuthProperties;
import com.lance.pix.basic.auth.domain.Authable;
import com.lance.pix.basic.auth.exception.AuthExpirationException;
import com.lance.pix.common.constant.AuthConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author lancer1126
 * @Date 2020-12-3
 * @Description JWT工具类
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JWTUtil implements Serializable {
    private final AuthProperties authProperties;

    public Claims getAllClaimsFromToken(String token) {
        JwtParser jwtParser = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(authProperties.getSecret().getBytes()));
        Claims body;
        try {
            body = jwtParser.parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new AuthExpirationException(HttpStatus.UNAUTHORIZED,"token无效");
        }
        return body;
    }

    public String getToken(Authable authable) {
        return generateToken(authable.getIssuer(),authable.getClaims());
    }

    private String refreshToken(Claims claims) {
        return generateToken(claims.getIssuer(), claims);
    }

    private String generateToken(String issuer, Map<String, Object> claims) {
        claims.merge(AuthConstant.REFRESH_COUNT,0,(value, newValue) -> (Integer)value < 3 ? (Integer)value + 1 : value);
        Integer refreshCount = (Integer) claims.get(AuthConstant.REFRESH_COUNT);
        long expirationTimeLong = Long.parseLong(authProperties.getExpirationTime());
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + (refreshCount + 1)*expirationTimeLong * 1000);
        return Jwts.builder()
                .setIssuer(issuer)
                .setClaims(claims)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(authProperties.getSecret().getBytes()))
                .compact();
    }

    /**
     * 成功返回user，失败抛出未授权异常
     * 刷新token时也在这里完成
     */
    public Map<String, Object> validateToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        long diff = claims.getExpiration().getTime() - System.currentTimeMillis();
        if (diff < 0) {
            //token无效
            throw new AuthExpirationException(HttpStatus.UNAUTHORIZED, "登录身份信息过期");
        }

        if (diff < authProperties.getRefreshInterval()) {
            //小于一定区间时刷新
            token = refreshToken(claims);
            claims.put("newToken", token);
        }
        return claims;
    }
}






















