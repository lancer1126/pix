package com.lance.pix.common.util.pixiv;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

/**
 * @Author lancer1126
 * @Date 2021-1-22
 * @Description OathResp
 */
@Data
public class OathResp {
    @JsonSetter("access_token")
    private String accessToken;

    @JsonSetter("device_token")
    private String deviceToken;

    @JsonSetter("refresh_token")
    private String refreshToken;
}