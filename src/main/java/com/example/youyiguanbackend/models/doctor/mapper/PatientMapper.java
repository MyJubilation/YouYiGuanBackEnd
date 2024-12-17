package com.example.youyiguanbackend.models.doctor.mapper;

import com.example.youyiguanbackend.models.doctor.model.dto.PatientGetListDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.PatientGetListVO;
import org.apache.ibatis.annotations.Mapper;

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
}
