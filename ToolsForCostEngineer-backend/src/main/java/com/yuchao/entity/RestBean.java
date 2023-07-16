package com.yuchao.entity;

import lombok.Data;

/**
 * @ClassName RestBean
 * @Description TODO
 * @Author YuChao
 * @Date 2023/7/16 10:54
 * @Version 1.0
 */
@Data
public class RestBean<T> {
    private int code;
    private boolean success;
    private T message;

    private RestBean(int code, boolean success, T message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }
    public static <T> RestBean<T> success() {
        return new RestBean<>(200, true, null);
    }
    public static <T> RestBean<T> success(T data) {
        return new RestBean<>(200, true, data);
    }
    public static <T> RestBean<T> failure(int code) {
        return new RestBean<>(code, false, null);
    }
    public static <T> RestBean<T> failure(int code, T data) {
        return new RestBean<>(code, false, data);
    }
}


