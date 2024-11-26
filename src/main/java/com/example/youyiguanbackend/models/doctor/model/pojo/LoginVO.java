package com.example.youyiguanbackend.models.doctor.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author beetles
 * @date 2024/11/26
 * @Description
 */
@Data
public class LoginVO {
    private String name;
    private String department;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime last_login;
}
