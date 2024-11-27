package com.example.youyiguanbackend.models.doctor.model.dto;

import lombok.Data;

/**
 * @author beetles
 * @date 2024/11/27
 * @Description
 */
@Data
public class DoctorPasswordUpdateDTO {
    private String current_password;
    private String new_password;
}
