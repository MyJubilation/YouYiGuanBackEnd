package com.example.youyiguanbackend.models.doctor.service;

import com.example.youyiguanbackend.models.doctor.model.dto.PatientGetListDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.PatientGetListVO;

import java.io.IOException;
import java.util.List;

/**
 * @author beetles
 * @date 2024/12/17
 * @Description
 */
public interface PatientService {
    List<PatientGetListVO> getPatientList(PatientGetListDTO dto) throws IOException;
}
