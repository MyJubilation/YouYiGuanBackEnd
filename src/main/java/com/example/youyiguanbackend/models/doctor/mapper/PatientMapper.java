package com.example.youyiguanbackend.models.doctor.mapper;

import com.example.youyiguanbackend.models.doctor.model.dto.PatientGetListDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.GetPatientInfoByPatientIdVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.MedicalRecords;
import com.example.youyiguanbackend.models.doctor.model.pojo.PatientGetListVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.PatientInfo;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author beetles
 * @date 2024/12/17
 * @Description
 */
@Mapper
public interface PatientMapper {
    String getDoctorUsernameById(int doctor_id);

    List<PatientGetListVO> getPatientList(PatientGetListDTO dto);

    List<MedicalRecords> getMedicalRecords(int patientId, int doctorId);

    PatientInfo getPatientInfo(int patientId);

    List<GetPatientInfoByPatientIdVO> getPatientInfoByPatientId(int patientId, String reviewStatus, String patientStatus, LocalDateTime dateFrom, LocalDateTime dateTo);
}
