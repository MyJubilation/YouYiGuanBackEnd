package com.example.youyiguanbackend.models.doctor.model.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiagnosisUpdateVO {
    private int record_id;
    private String symptoms;
    private String diagnosis;
    private String recipe_name;
    private String recipe_details;
    private String paozhi;
    private String remarks;
    private String patient_status;
    private String payment_status;
    private String review_status;
    private LocalDateTime visit_date;
}
