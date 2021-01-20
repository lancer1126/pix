package com.lance.pix.common.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @Author lancer1126
 * @Date 2020-12-5
 * @Description 公共响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Result<T> {
    @JsonIgnore
    private HttpStatus httpStatus;
    private String message;
    private Long total;
    private T data;

    public Result(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public Result(String message) {
        this.message = message;
    }

    public Result(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public Result(String message, Long total, T data) {
        this.message = message;
        this.total = total;
        this.data = data;
    }

    public Result(String message, Integer total, T data) {
        this.message = message;
        if (total != null) {
            this.total = Long.valueOf(total);
        }
        this.data = data;
    }

    public void set(HttpStatus httpStatus, String message, T data) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }
}