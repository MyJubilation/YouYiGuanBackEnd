package com.example.youyiguanbackend.models.doctor.service.serviceImpl;

import com.example.youyiguanbackend.models.doctor.mapper.ExtraMapper;
import com.example.youyiguanbackend.models.doctor.model.dto.DiagnoseDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.DiagnosisUpdateDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.GetApplicationDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.*;
import com.example.youyiguanbackend.models.doctor.service.ExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtraServiceImpl implements ExtraService {

    @Autowired
    private ExtraMapper extraMapper;

    @Override
    public GetApplicationVO appointmentConfirm(GetApplicationDTO applicationDTO) {
        // 修改appointment的status
        int cnt = extraMapper.setAppointment(applicationDTO.getAppointment_id());
        if(cnt != 0){
            // 获取appointment值
            Appointment appointment = extraMapper.getAppointment(applicationDTO.getAppointment_id());
            // 获取medical_record值
            MedicalRecordPOJO medicalRecordPOJO = new MedicalRecordPOJO();
            medicalRecordPOJO.setPatient_id(appointment.getPatient_id());
            medicalRecordPOJO.setDoctor_id(appointment.getDoctor_id());
            extraMapper.getMedicalRecordId(medicalRecordPOJO);
            MedicalRecord medicalRecord = extraMapper.getMedicalRecord(medicalRecordPOJO.getRecord_id());
            // 赋值给VO并返回
            GetApplicationVO getApplicationVO = new GetApplicationVO();
            getApplicationVO.setAppointment(appointment);
            getApplicationVO.setMedical_record(medicalRecord);
            return getApplicationVO;
        }else {
            return null;
        }
    }

    @Override
    public DiagnoseVO diagnose(DiagnoseDTO diagnoseDTO) {
        return extraMapper.diagnose(diagnoseDTO);
    }

    @Override
    public DiagnosisUpdateVO diagnosisUpdate(int recordId, DiagnosisUpdateDTO diagnosisUpdateDTO, String username) {
        // 判断查询username与record_id条件下有无值,且状态为 review_status = '待审核',有则表示该医生可修改
        Integer cnt = extraMapper.selectByRecordIdAndUserNameAndReviewStatus(recordId,username);
        if(cnt != null){
            extraMapper.diagnosisUpdate(recordId,diagnosisUpdateDTO);
            DiagnosisUpdateVO diagnosisUpdateVO = extraMapper.selectByRecordId(recordId);
            return diagnosisUpdateVO;
        }else {
            return null;
        }
    }
}
