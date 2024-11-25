package com.example.youyiguanbackend.models.doctor.model.pojo;

import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Department;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.ExperienceLevel;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Gender;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Status;
import lombok.Data;

import java.sql.Blob;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author beetles
 * @date 2024/11/20
 * @Description
 */
@Data
public class Doctor {
    private int doctor_id;
    private String name;
    private Gender gender;
    private int age;
    private ExperienceLevel experienceLevel;   // 医生职称级别
    private String hospitalName;   // 所属医院名称
    private String contactNumber;   // 联系电话
    private String email;
    private String username;
    private String password;
    private String faceRecognitionData ;   // 医生的面部识别数据
    private Status status;   // 医生账户的状态，”激活“表示账户已启用，'未激活'表示账户未启用
    private LocalDateTime accountCreationDate;   // 用户创建时间
    private LocalDateTime lastLogin;  // 最后一次登录时间
    private int permissionLevel;  // 医生权限级别
    private int aiDiagnosisReviewEnabled;  // 是否启用ai诊断审核功能
    private String remarks;  // 医生备注信息
    private String faceImageUrl;  // 人脸图片
    private Department department;  //科室
    private String faceToken;  // 唯一面部id
}
