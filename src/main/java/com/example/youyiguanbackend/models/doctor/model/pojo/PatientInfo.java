package com.example.youyiguanbackend.models.doctor.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author beetles
 * @date 2024/12/18
 * @Description
 */
@Data
public class PatientInfo {
    private int patient_id;
    private String name;
    private String gender;
    private int age;
    private float height;
    private float weight;
    private String tongue_condition;
    private String pulse_condition;
    private String symptoms;
    private String diagnosis_history;
    private String contact_number;
    private String email;
    private String registration_date;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime last_visit;
    // private List<MedicalRecords> medical_records;
}
