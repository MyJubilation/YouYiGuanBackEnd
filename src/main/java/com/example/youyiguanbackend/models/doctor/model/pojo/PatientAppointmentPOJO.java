package com.example.youyiguanbackend.models.doctor.model.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PatientAppointmentPOJO {
    private int appointment_id;
    private int patient_id;
    private int doctor_id;
    private LocalDateTime appointment_time;
    private String status;
    private int queue_number;
    private LocalDateTime created_at;
}
