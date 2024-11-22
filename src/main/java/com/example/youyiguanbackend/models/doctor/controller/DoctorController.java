package com.example.youyiguanbackend.models.doctor.controller;

import com.example.youyiguanbackend.common.doctor.Result.Result;
import com.example.youyiguanbackend.common.doctor.Util.RandomUtil;
import com.example.youyiguanbackend.models.doctor.dto.RegisterDTO;
import com.example.youyiguanbackend.models.doctor.pojo.Doctor;
import com.example.youyiguanbackend.models.doctor.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @author beetles
 * @date 2024/11/20
 * @Description
 */
@RestController
@RequestMapping("/api/doctor")
/**
 * 医生功能
 */
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/request-verification-code")
    /**
     * 给用户手机发送短信
     */
    public Result<?> requestVerificationCode(@RequestBody Map<String, String> phoneNumberMap) {
        String phoneNumber = phoneNumberMap.get("phoneNumber");
        // 获取验证码并发送
        if(doctorService.sendCode(phoneNumber)){
            return Result.success().code(200).message("验证码已发送");
        }else {
            return Result.error(401,"手机号格式错误或发送失败");
        }
    }

    @PostMapping("/verify-code")
    /**
     * 验证用户传过来的验证码和手机号是否正确
     */
    public Result<?> verifyCode(@RequestBody Map<String, String> phoneNumberMap) {

        // 获取到api传来的电话号码和验证码
        String phoneNumber = phoneNumberMap.get("phone_number");
        String VCode = phoneNumberMap.get("verification_code");

        if(doctorService.checkVCode(phoneNumber,VCode)){
            return Result.success().code(200).message("验证码验证成功");
        }else {
            return Result.error(401,"验证码错误或已过期");
        }
    }

}
