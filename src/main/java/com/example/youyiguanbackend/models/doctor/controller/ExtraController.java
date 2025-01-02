package com.example.youyiguanbackend.models.doctor.controller;

import com.example.youyiguanbackend.common.doctor.Result.Result;
import com.example.youyiguanbackend.models.doctor.model.dto.DiagnoseDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.DiagnosisUpdateDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.GetApplicationDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.DiagnoseVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.DiagnosisUpdateVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.GetApplicationVO;
import com.example.youyiguanbackend.models.doctor.service.ExtraService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api")
public class ExtraController {

    @Autowired
    private ExtraService extraService;

    /**
     * 医生确认预约并更新数据库
     */
    @PostMapping("/appointment/confirm/{appointmentId}")
    public Result<?> appointmentConfirm(@PathVariable int appointmentId) {
        // @RequestBody GetApplicationDTO applicationDTO 原api输入
//        GetApplicationVO vo = extraService.appointmentConfirm(applicationDTO);
        int result = extraService.appointmentConfirmNew(appointmentId);
        /**
         * 新更新的api中不用返回vo
         * 如果需要返回，则直接在返回data加上vo
         */
        if(result != 0) {
            return Result.success();
        }else {
            return Result.error(401,"预约确认失败，请检查参数");
        }
    }
    /**
     * 医生为病人更新信息
     */
    @PutMapping("/doctors/diagnose/updatepatient")
    public Result<?> updatepatient(@RequestBody DiagnoseDTO diagnoseDTO) {
//        DiagnoseVO diagnoseVO = extraService.diagnose(diagnoseDTO);
        int result = extraService.updatepatient(diagnoseDTO);
        if(result != 0) {
            return Result.success().message("诊断信息更新成功");
        }else {
            return Result.error(401,"修改失败");
        }
    }
    /**
     * 医生为病人下诊断结论
     */
    @PutMapping("/doctors/diagnose/conclusion")
    public Result<?> conclusion(@RequestBody DiagnosisUpdateDTO diagnosisUpdateDTO) {
        int result = extraService.conclusion(diagnosisUpdateDTO);
        if(result != 0){
            return Result.success();
        }else {
            return Result.error(401,"诊断失败");
        }
    }

    /**
     * 修改诊断记录
     */
    @PutMapping("/diagnosis/{record_id}")
    public Result<?> diagnosisUpdate(@PathVariable int record_id,
                                     @RequestBody DiagnosisUpdateDTO diagnosisUpdateDTO,
                                     @RequestHeader("Authorization") String authorizationHeader) throws IOException {
        // 获取token值，存储为String类型
        String token = authorizationHeader.substring(7);
        // 解析token中的username
        String username = getUserNameByToken(token);
        DiagnosisUpdateVO diagnosisUpdateVO = extraService.diagnosisUpdate(record_id,diagnosisUpdateDTO,username);
        if(diagnosisUpdateVO != null) {
            return Result.success().message("诊断记录修改成功");
        }else {
            return Result.error(401,"诊断记录无法修改，当前状态不允许修改");
        }
    }
    /**
     * 医生审核诊断记录
     */
    @PutMapping("/diagnosis/audit/{record_id}")
    public Result<?> audit(@PathVariable int record_id){
        int result = extraService.audit(record_id);
        if(result != 0) {
            return Result.success().message("该诊断记录状态改为已审核");
        }else {
            return Result.error(500,"错误");
        }
    }
    /**
     * TODO 智能审核药方
     */


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
