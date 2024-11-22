package com.example.youyiguanbackend.models.doctor.dto;

import com.example.youyiguanbackend.models.doctor.dto.Enum.Department;
import com.example.youyiguanbackend.models.doctor.dto.Enum.ExperienceLevel;
import com.example.youyiguanbackend.models.doctor.dto.Enum.Gender;
import com.example.youyiguanbackend.models.doctor.dto.Enum.Status;
import lombok.Data;

import java.sql.Blob;
import java.sql.Timestamp;

/**
 * @author beetles
 * @date 2024/11/20
 * @Description
 */
@Data
public class RegisterDTO {

    private String name;  //姓名
    private Gender gender;  //性别
    private int age;  //年龄
    private ExperienceLevel experienceLevel;  //职称
    private String hospitalName;  //医院名称
    private int contactNumber;  //联系电话
    private String email;  //邮箱
    private String username;  //用户名
    private String password;  //用户密码
    private Department department;  //科室
    private String faceImageBase64;  //人脸识别图片


// Default constructor
    public RegisterDTO() {
    }


}
