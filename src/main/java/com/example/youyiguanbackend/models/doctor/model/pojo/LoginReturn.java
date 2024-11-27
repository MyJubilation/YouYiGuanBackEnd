package com.example.youyiguanbackend.models.doctor.model.pojo;

import lombok.Data;

/**
 * @author beetles
 * @date 2024/11/27
 * @Description
 */
@Data
public class LoginReturn {
    private String token;
    private LoginVO doctor_info;
}
