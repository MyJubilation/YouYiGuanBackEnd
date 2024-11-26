package com.example.youyiguanbackend.models.doctor.model.dto;

import lombok.Data;

/**
 * @author beetles
 * @date 2024/11/26
 * @Description
 */
@Data
public class LoginByFaceDTO {
    private String username;
    private String face_image_base64;
}
