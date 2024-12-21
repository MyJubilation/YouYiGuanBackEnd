package com.example.youyiguanbackend.models.doctor.service;

import com.example.youyiguanbackend.models.doctor.model.dto.PatientGetListDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.GetPatientInfoByPatientIdVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.PatientGetListVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.PatientInfo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author beetles
 * @date 2024/12/17
 * @Description
 */
public interface PatientService {
    List<PatientGetListVO> getPatientList(PatientGetListDTO dto) throws IOException;

    PatientInfo getPatientInfo(int doctorId, int patientId);

    List<GetPatientInfoByPatientIdVO> getPatientInfoByPatientId(int patientId, String reviewStatus, String patientStatus, LocalDateTime dateFrom, LocalDateTime dateTo);
}
