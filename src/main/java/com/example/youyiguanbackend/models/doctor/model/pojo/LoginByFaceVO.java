package com.example.youyiguanbackend.models.doctor.model.pojo;

import lombok.Data;

/**
 * @author beetles
 * @date 2024/11/26
 * @Description
 */
@Data
public class LoginByFaceVO {
    private String username;
    private String faceToken;
}