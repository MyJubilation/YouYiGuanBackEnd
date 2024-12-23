package com.example.youyiguanbackend.models.doctor.model.dto;

import lombok.Data;

@Data
public class DiagnosisUpdateDTO {
    private String symptoms;
    private String diagnosis;
    private String recipe_name;
    private String recipe_details;
    private String paozhi;
    private String remarks;
    private String patient_status;
    private String payment_status;
}
