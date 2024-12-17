package com.example.youyiguanbackend.models.doctor.model.dto;

import lombok.Data;

/**
 * @author beetles
 * @date 2024/12/17
 * @Description
 */
@Data
public class PatientGetListDTO {
    private int doctor_id;      // 当前登录医生的 ID
    private String search;      // 根据病人姓名查询（模糊搜索）
    private String review_status;   // 筛选诊断记录的审核状态：待审核，已审核
    private String patient_status;  // 筛选病人状态：待确认，已确认
    private String appointment_status;  // 筛选预约状态：待诊断，诊断中，已诊断，待确认，已取消
    private String token;
}
