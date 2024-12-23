package com.example.youyiguanbackend.models.doctor.model.dto;


import lombok.Data;

@Data
public class GetApplicationDTO {
    private int appointment_id;
    private int doctor_id;
    private String confirmation_notes;  // 医生确认的备注信息，更新到日志或备注
}
