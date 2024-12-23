package com.example.youyiguanbackend.models.doctor.model.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Appointment {
   private int appointment_id;
   private int patient_id;
   private int doctor_id;
   private LocalDateTime appointment_time;
   private String status;
   private int queue_number;
}
