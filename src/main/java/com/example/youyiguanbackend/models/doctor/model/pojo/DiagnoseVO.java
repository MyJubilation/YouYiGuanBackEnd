package com.example.youyiguanbackend.models.doctor.model.pojo;

import lombok.Data;

@Data
public class DiagnoseVO {
    private int patient_id;
    private String tongue_condition;
    private String pulse_condition;
    private String symptoms;
    private float height;
    private float weight;
}
