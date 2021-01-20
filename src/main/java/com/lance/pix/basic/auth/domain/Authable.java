package com.lance.pix.basic.auth.domain;

import java.util.Map;

/**
 * @Author lancer1126
 * @Date 2020-12-3
 * @Description 权限约定
 */
public interface Authable {

    String getIssuer();

    Map<String, Object> getClaims();

    boolean isEnabled();
}