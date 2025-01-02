package com.example.youyiguanbackend.models.doctor.service;

import com.example.youyiguanbackend.models.doctor.model.dto.DiagnoseDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.DiagnosisUpdateDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.GetApplicationDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.DiagnoseVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.DiagnosisUpdateVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.GetApplicationVO;

public interface ExtraService {
    GetApplicationVO appointmentConfirm(GetApplicationDTO applicationDTO);

    DiagnoseVO diagnose(DiagnoseDTO diagnoseDTO);

    DiagnosisUpdateVO diagnosisUpdate(int recordId, DiagnosisUpdateDTO diagnosisUpdateDTO, String username);

    int appointmentConfirmNew(int appointmentId);

    int updatepatient(DiagnoseDTO diagnoseDTO);

    int audit(int recordId);

    int conclusion(DiagnosisUpdateDTO diagnosisUpdateDTO);
}
