package com.example.youyiguanbackend.models.doctor.mapper;

import com.example.youyiguanbackend.models.doctor.model.dto.DiagnoseDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.DiagnosisUpdateDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.GetApplicationDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExtraMapper {
    int setAppointment(int appointmentId);

    Appointment getAppointment(int appointmentId);

    int getMedicalRecordId(MedicalRecordPOJO medicalRecordPOJO);

    MedicalRecord getMedicalRecord(int recordId);

    DiagnoseVO diagnose(DiagnoseDTO diagnoseDTO);

    Integer selectByRecordIdAndUserNameAndReviewStatus(int recordId, String username);

    void diagnosisUpdate(int recordId, DiagnosisUpdateDTO diagnosisUpdateDTO);

    DiagnosisUpdateVO selectByRecordId(int recordId);
}
