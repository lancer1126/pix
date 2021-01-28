package com.lance.pix.biz.web.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author lancer1126
 * @Date 2021-1-28
 * @Description TODO
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserListDTO {
    private String username;
    private Integer userId;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createDate;

}