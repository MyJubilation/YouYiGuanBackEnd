package com.example.youyiguanbackend.models.doctor.controller;

import com.example.youyiguanbackend.common.doctor.Result.Result;
import com.example.youyiguanbackend.common.doctor.Util.ConstantUtil;
import com.example.youyiguanbackend.models.doctor.model.dto.LoginDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.RegisterDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.LoginVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.RegisterVO;
import com.example.youyiguanbackend.models.doctor.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
        String phoneNumber = phoneNumberMap.get("phone_number");
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

    @PostMapping("/validate-face")
    /**
     * 验证上传的人脸图片数据的质量,同时将人脸数据存储
     */
    public Result<?> validateFace(@RequestBody Map<String, String> faceImageBase64Map) throws IOException {
        // 获取上传的人脸图片，转换为base64编码
        String base64String = faceImageBase64Map.get("face_image_base64");
        int type = doctorService.validateFace(base64String);
        if(type == ConstantUtil.FaceIdSetSuccess){
            return Result.success().code(200).message("人脸数据验证成功");
        }else if(type == ConstantUtil.FaceIdAlreadyExist) {
            return Result.error(222203,"上传的人脸数据已经录入");
        }else {
            return Result.error(401,"上传的人脸数据质量不合格");
        }
    }

    @PostMapping("/register")
    /**
     * 医生注册相关功能
     */
    public Result<?> register(@RequestBody RegisterDTO registerDTO) {
        // TODO 接收存入后的doctor_id和status
        RegisterVO registerVO = doctorService.register(registerDTO);
        if(registerVO != null){
            return Result.success().code(200).message("注册成功").data(registerVO);
        }else {
            return Result.error(401,"注册失败，请检查输入信息");
        }
    }

    @PostMapping("/login")
    /**
     * 医生用户名密码登录
     */
    public Result<?> loginByUsername(@RequestBody LoginDTO loginDTO) {
        // 登录操作，返回LoginVO数据类型
        LoginVO loginVO = doctorService.loginByUsername(loginDTO);
        // jwt封装
        // 返回值，将jwt和LoginVO给put到Result中
        // test
        if(loginVO != null){
            return Result.success().code(200).data(loginVO).message("登录成功");
        }else {
            return Result.error(401,"用户名或密码错误");
        }
    }

}
