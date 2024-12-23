package com.example.youyiguanbackend.models.doctor.model.pojo;

import lombok.Data;

@Data
public class GetApplicationVO {
    private Appointment appointment;
    private MedicalRecord medical_record;
}
