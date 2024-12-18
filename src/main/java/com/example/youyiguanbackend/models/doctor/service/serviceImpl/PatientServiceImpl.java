package com.example.youyiguanbackend.models.doctor.service.serviceImpl;

import com.example.youyiguanbackend.models.doctor.mapper.PatientMapper;
import com.example.youyiguanbackend.models.doctor.model.dto.PatientGetListDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.MedicalRecords;
import com.example.youyiguanbackend.models.doctor.model.pojo.PatientGetListVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.PatientInfo;
import com.example.youyiguanbackend.models.doctor.service.PatientService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * @author beetles
 * @date 2024/12/17
 * @Description
 */
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public List<PatientGetListVO> getPatientList(PatientGetListDTO dto) throws IOException {
        String username = getUserNameByToken(dto.getToken());
        // 判断当前token的username值是否和doctor_id对应
        if(Objects.equals(patientMapper.getDoctorUsernameById(dto.getDoctor_id()), username)){
            // 验证token成功
            return patientMapper.getPatientList(dto);
        }else {
            return null;
        }
    }

    @Override
    public PatientInfo getPatientInfo(int doctor_id, int patient_id) {
        // // 查询药方记录
        // List<MedicalRecords> medicalRecords = patientMapper.getMedicalRecords(patient_id,doctor_id);
        // 查询patient
        PatientInfo patientInfo = patientMapper.getPatientInfo(patient_id);
        // // 将记录存入patient
        // patientInfo.setMedical_records(medicalRecords);
        return patientInfo;
    }

    /**
     * 从token中解析并获取username
     */
    static String getUserNameByToken(String token) throws IOException {
        String[] parts = token.split("\\.");
        String payload = parts[1]; // 获取payload部分

        // 将Base64URL编码转换为标准的Base64编码
        String payloadBase64 = payload.replace('-', '+').replace('_', '/');
        // Base64解码
        byte[] payloadBytes = Base64.getDecoder().decode(payloadBase64);

        String payloadString = new String(payloadBytes);

        // 创建ObjectMapper实例
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // 将字符串转换为JsonNode
            JsonNode rootNode = objectMapper.readTree(payloadString);
            // 获取"username"字段
            JsonNode usernameNode = rootNode.path("username");
            // 获取"username"字段的值
            if (usernameNode.isTextual()) {
                String username = usernameNode.asText();
                return username;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
