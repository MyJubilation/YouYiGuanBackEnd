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
     * @param applicationDTO
     * @return Result
     */
    @PostMapping("/appointment/confirm")
    public Result<?> appointmentConfirm(@RequestBody GetApplicationDTO applicationDTO) {
        GetApplicationVO vo = extraService.appointmentConfirm(applicationDTO);
        if(vo != null) {
            return Result.success(vo);
        }else {
            return Result.error(401,"预约确认失败，请检查参数");
        }
    }
    /**
     * 医生为病人诊断
     * TODO 一些问题未解决
     */
    @PutMapping("/doctors/diagnose")
    public Result<?> diagnose(@RequestBody DiagnoseDTO diagnoseDTO) {
        DiagnoseVO diagnoseVO = extraService.diagnose(diagnoseDTO);
        if(diagnoseVO != null) {
            return Result.success(diagnoseVO);
        }else {
            return Result.error(401,"修改失败");
        }
    }
    /**
     * 修改诊断记录
     *  TODO symptom属性在数据库中不存在
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
            return Result.success(diagnosisUpdateVO);
        }else {
            return Result.error(401,"诊断记录无法修改，当前状态不允许修改");
        }
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
