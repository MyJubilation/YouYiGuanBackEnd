package com.example.youyiguanbackend.models.doctor.model.dto;

import lombok.Data;

/**
 * @author beetles
 * @date 2024/11/27
 * @Description
 */
@Data
public class DoctorUpdateDTO {
    private String name;
    private String gender;
    private int age;
    private String experience_level;
    private String hospital_name;
    private String department;
    private String contact_number;
    private String email;
}
