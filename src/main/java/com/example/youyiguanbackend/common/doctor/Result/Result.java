package com.example.youyiguanbackend.common.doctor.Result;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 后端统一返回结果
 *
 * @param <T> 数据类型
 */
@Data
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code; // 状态码
    private String msg;   // 错误信息
    private T data;       // 返回数据

    // 构造方法
    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 成功返回，无数据
    public static <T> Result<T> success() {
        return new Result<>(HttpStatus.SUCCESS.getCode(), HttpStatus.SUCCESS.getMessage(), null);
    }

    // 成功返回，有数据
    public static <T> Result<T> success(T data) {
        return new Result<>(HttpStatus.SUCCESS.getCode(), HttpStatus.SUCCESS.getMessage(), data);
    }

    // 自定义成功返回
    public static <T> Result<T> success(T data, String msg) {
        return new Result<>(HttpStatus.SUCCESS.getCode(), msg, data);
    }

    // 错误返回，自定义错误消息
    public static <T> Result<T> error(String msg) {
        return new Result<>(HttpStatus.ERROR.getCode(), msg, null);
    }

    // 错误返回，自定义状态码和错误消息
    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    // 支持链式调用的 Builder 模式
    public Result<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public Result<T> message(String msg) {
        this.msg = msg;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    /**
     * 状态码枚举
     */
    public enum HttpStatus {
        SUCCESS(200, "操作成功"),
        ERROR(500, "服务器内部错误"),
        NOT_FOUND(404, "资源未找到"),
        FORBIDDEN(403, "禁止访问"),
        UNAUTHORIZED(401, "未授权");

        private final Integer code;
        private final String message;

        HttpStatus(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}

