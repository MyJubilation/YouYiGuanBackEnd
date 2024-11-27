package com.example.youyiguanbackend.models.doctor.model.pojo;

import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Department;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.ExperienceLevel;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Gender;
import lombok.Data;

/**
 * @author beetles
 * @date 2024/11/27
 * @Description
 */
@Data
public class DoctorUpdateVO {
    private String username;
    private String name;
    private Gender gender;
    private int age;
    private ExperienceLevel experience_level;
    private String hospital_name;
    private Department department;
    private String contact_number;
    private String email;
}
