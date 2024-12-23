package com.example.youyiguanbackend.models.doctor.model.dto;

import lombok.Data;

@Data
public class DiagnoseDTO {
    private int doctor_id;
    private int patient_id;
    private String tongue_condition;
    private String pulse_condition;
    private String symptoms;
    private float height;
    private float weight;
}
