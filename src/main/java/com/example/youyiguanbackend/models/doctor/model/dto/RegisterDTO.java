package com.example.youyiguanbackend.models.doctor.model.dto;

import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Department;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.ExperienceLevel;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Gender;
import lombok.Data;

/**
 * @author beetles
 * @date 2024/11/20
 * @Description
 */
@Data
public class RegisterDTO {

    private String name;  //姓名
    private String gender;  //性别
    private int age;  //年龄
    private String experience_level;  //职称
    private String hospital_name;  //医院名称
    private String contact_number;  //联系电话
    private String email;  //邮箱
    private String username;  //用户名
    private String password;  //用户密码
    private String department;  //科室
    private String face_image_ase64;  //人脸识别图片


// Default constructor
    public RegisterDTO() {
    }


}
