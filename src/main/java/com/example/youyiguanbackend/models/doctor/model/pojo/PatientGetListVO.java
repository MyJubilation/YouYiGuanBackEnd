package com.example.youyiguanbackend.models.doctor.model.pojo;

import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author beetles
 * @date 2024/12/17
 * @Description
 */
@Data
public class PatientGetListVO {
    private int patient_id;
    /**
     * 从patient表获取
     */
    private String name;
    private String gender;
    private int age;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate last_visit;
    /**
     * 从medicalrecord表获取
     */
    private String review_status;
    private String patient_status;
    /**
     * 从patientappointment表获取
     */
    private String appointment_status;
}
