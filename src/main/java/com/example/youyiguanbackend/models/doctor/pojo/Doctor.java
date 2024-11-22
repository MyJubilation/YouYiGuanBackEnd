package com.example.youyiguanbackend.models.doctor.pojo;

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
public class Doctor {
    private String name;
    private Gender gender;
    private int age;
    private ExperienceLevel experienceLevel;
    private String hospitalName;
    private String contactNumber;
    private String email;
    private String username;
    private String password;
    private Blob faceImageBase64;
    private Status status;
    private Timestamp accountCreationDate;
    private Timestamp lastLogin;
    private int permissionLevel;
    private int aiDiagnosisReviewEnabled;
    private String remarks;
    private String faceImageUrl;
    private Department department;
}
