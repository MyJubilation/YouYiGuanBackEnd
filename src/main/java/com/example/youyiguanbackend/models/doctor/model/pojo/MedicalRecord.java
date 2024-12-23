package com.example.youyiguanbackend.models.doctor.model.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicalRecord {
    private int record_id;
    private int patient_id;
    private int doctor_id;
    private String recipe_name;
    private String diagnosis;
    private String review_status;
    private LocalDateTime visit_date;
}
