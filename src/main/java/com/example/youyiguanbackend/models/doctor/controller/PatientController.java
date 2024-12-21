package com.example.youyiguanbackend.models.doctor.controller;

import com.example.youyiguanbackend.common.doctor.Result.Result;
import com.example.youyiguanbackend.models.doctor.model.dto.PatientGetListDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.GetPatientInfoByPatientIdVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.PatientGetListVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.PatientInfo;
import com.example.youyiguanbackend.models.doctor.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author beetles
 * @date 2024/12/17
 * @Description 医生对病人操作相关功能
 */
@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    /**
     * 获取病人列表 Get
     */
    @GetMapping("/list")
    public Result<?> getPatientList(@RequestHeader("Authorization") String authorizationHeader,
                                    @RequestParam("doctor_id") int doctorId,     // 当前登录医生的 ID
                                    @RequestParam(value = "search", required = false) String search,    // 根据病人姓名或病历号查询（模糊搜索）
                                    @RequestParam(value = "review_status", required = false) String reviewStatus,   // 筛选诊断记录的审核状态：待审核，已审核
                                    @RequestParam(value = "patient_status", required = false) String patientStatus, // 筛选病人状态：待确认，已确认
                                    @RequestParam(value = "appointment_status", required = false) String appointmentStatus) throws IOException { // 筛选预约状态：待诊断，诊断中，已诊断，待确认，已取消
        // 获取token，存储为String值
        String token = authorizationHeader.substring(7);
        // 存储值到对象中，传递到service层
        PatientGetListDTO dto = new PatientGetListDTO();
        dto.setDoctor_id(doctorId);
        dto.setSearch(search);
        dto.setReview_status(reviewStatus);
        dto.setPatient_status(patientStatus);
        dto.setAppointment_status(appointmentStatus);
        dto.setToken(token);

        List<PatientGetListVO> result = patientService.getPatientList(dto);
        if(result!=null && result.size()>0){
            return Result.success(result);
        }else {
            return Result.error(401,"获取失败，请检查输入条件");
        }
    }
    /**
     * 病人详细信息
     */
    @GetMapping("/detail")
    public Result<?> getPatientInfo(@RequestHeader("Authorization") String authorizationHeader,
                                    @RequestParam(value = "doctor_id") int doctor_id,
                                    @RequestParam(value = "patient_id") int patient_id) throws IOException {
        PatientInfo result = patientService.getPatientInfo(doctor_id,patient_id);
        if(result!=null){
            return Result.success(result);
        }else {
            return Result.error(1001,"获取失败，参数错误");
        }
    }
    /**
     * 获取指定病人诊断信息
     */
    @GetMapping("/{patient_id}/medical-records")
    public Result<?> getPatientInfoByPatientId(@PathVariable int patient_id,
                                               @RequestParam(value = "review_status", required = false) String review_status,
                                               @RequestParam(value = "patient_status", required = false) String patient_status,
                                               @RequestParam(value = "date_from", required = false) String Bdate_from,
                                               @RequestParam(value = "date_to", required = false) String Bdate_to){
        LocalDateTime date_from = null;
        LocalDateTime date_to = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(Bdate_from != null){
            LocalDate localDateFrom = LocalDate.parse(Bdate_from, formatter);
            date_from = localDateFrom.atStartOfDay(); // 将LocalDate转换为LocalDateTime，时间为00:00:00
        }
        if(Bdate_to != null){
            LocalDate localDateTo = LocalDate.parse(Bdate_to, formatter);
            date_to = localDateTo.atStartOfDay().plusDays(1).minusNanos(1); // 将LocalDate转换为LocalDateTime，时间为23:59:59.999999999
        }
        List<GetPatientInfoByPatientIdVO> list = patientService.getPatientInfoByPatientId(patient_id,review_status,patient_status,date_from,date_to);
        if(!list.isEmpty()){
            return Result.success(list);
        }else {
            return Result.error(401,"该病人没有关联的诊断记录");
        }
    }
}
