package com.example.youyiguanbackend.models.doctor.model.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicalRecordPOJO {
    private int record_id;
    private int patient_id;
    private int doctor_id;
    private int recipe_reference_id;
    private String recipe_type;
    private String recipe_name;
    private String recipe_details;
    private String paozhi;
    private String diagnosis;
    private String review_status = "待审核";
    private String remarks;
    private LocalDateTime visit_date = LocalDateTime.now();
    private String patient_status = "待确认";
    private String payment_status = "待支付";
    private float price;
}
