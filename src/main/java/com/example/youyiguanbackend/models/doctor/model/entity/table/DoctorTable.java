package com.example.youyiguanbackend.models.doctor.model.entity.table;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Department;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.ExperienceLevel;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Gender;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Blob;
import java.sql.Timestamp;

/**
 * @author beetles
 * @date 2024/11/25
 * @Description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("doctor")
public class DoctorTable {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    private String name;
    @TableField("gender")
    private Gender gender;
    @TableField("age")
    private int age;
    @TableField("experience_level")
    private ExperienceLevel experienceLevel;   // 医生职称级别
    @TableField("hospital_name")
    private String hospitalName;   // 所属医院名称
    @TableField("contact_number")
    private String contactNumber;   // 联系电话
    @TableField("email")
    private String email;
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("face_recognition_data")
    private Blob faceRecognitionData ;   // 医生的面部识别数据
    @TableField("status")
    private Status status;   // 医生账户的状态，”激活“表示账户已启用，'未激活'表示账户未启用
    @TableField("account_creation_date")
    private Timestamp accountCreationDate;   // 用户创建时间
    @TableField("last_login")
    private Timestamp lastLogin;  // 最后一次登录时间
    @TableField("permission_level")
    private int permissionLevel;  // 医生权限级别
    @TableField("ai_diagnosis_review_enabled")
    private int aiDiagnosisReviewEnabled;  // 是否启用ai诊断审核功能
    @TableField("remarks")
    private String remarks;  // 医生备注信息
    @TableField("face_image_url")
    private String faceImageUrl;  // 人脸图片
    @TableField("department")
    private Department department;  //科室
    @TableField("face_token")
    private String faceToken;  // 唯一面部id
}
