package com.example.youyiguanbackend.models.doctor.model.pojo;

import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Department;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.ExperienceLevel;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Gender;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author beetles
 * @date 2024/11/27
 * @Description
 */
@Data
public class DoctorInfoVO {
    private String name;
    private String gender;
    private int age;
    private String experience_level;   // 医生职称级别
    private String hospital_name;   // 所属医院名称
    private String department;  //科室
    private String contact_number;   // 联系电话
    private String email;
    private String permission_level;
    private String status;   // 医生账户的状态，”激活“表示账户已启用，'未激活'表示账户未启用
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime last_login;  // 最后一次登录时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime account_creation_date;   // 用户创建时间
}
