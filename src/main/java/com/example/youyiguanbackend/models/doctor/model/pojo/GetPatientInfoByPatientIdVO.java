package com.example.youyiguanbackend.models.doctor.model.pojo;

import lombok.Data;

@Data
public class GetPatientInfoByPatientIdVO {
    private int record_id;
    private String doctor_name;
    private String recipe_name; // 处方名称
    private String recipe_details;  // 处方详情
    private String paozhi;  // 炮制方法
    private String diagnosis;   // 医生诊断意见
    private String review_status;   // 审核状态（已审核/待审核）
    private String patient_status;  // 病人状态（待确认/已确认）
    private String visit_date;  // 就诊日期
    private String remarks; // 备注
}
