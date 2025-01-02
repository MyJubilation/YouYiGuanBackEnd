package com.example.youyiguanbackend.models.doctor.controller;

import com.example.youyiguanbackend.common.doctor.Result.Result;
import com.example.youyiguanbackend.common.doctor.Util.ConstantUtil;
import com.example.youyiguanbackend.common.doctor.Util.JWTUtil;
import com.example.youyiguanbackend.models.doctor.model.dto.*;
import com.example.youyiguanbackend.models.doctor.model.pojo.*;
import com.example.youyiguanbackend.models.doctor.service.DoctorService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
 * TODO 人脸库相关功能api投入生产环境时需要更换，当前为个人用户人脸库，企业使用时有隐患
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
        // 接收存入后的doctor_id和status
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
        // jwt封装
        Map<String,Object> map = new HashMap<>();
        try{
            // 登录操作，返回LoginVO数据类型
            LoginVO loginVO = doctorService.loginByUsername(loginDTO);
            JWTVO jwtVO = doctorService.selectJwtVO(loginDTO.getUsername());
            if(loginVO != null){
                // 返回值，将jwt和LoginVO给put到Result中
                Map<String,String> payload = new HashMap<>();
                payload.put("name",loginVO.getName());
                payload.put("username",jwtVO.getUsername());
                payload.put("department",loginVO.getDepartment());
                //生成JWT令牌
                String token = JWTUtil.getToken(payload);
                // 将token封装到VO中
                LoginReturn loginReturn = new LoginReturn();
                loginReturn.setToken(token);
                loginReturn.setDoctor_info(loginVO);
                return Result.success().code(200).data(loginReturn).message("登录成功");
            }else {
                return Result.error(401,"用户名或密码错误");
            }
        } catch (Exception e) {
            return Result.error(404, e.getMessage());
        }


    }

    @PostMapping("/login/face")
    /**
     * 人脸识别登录
     */
    public Result<?> loginByFace(@RequestBody LoginByFaceDTO loginByFaceDTO) {
        // jwt封装
        Map<String,Object> map = new HashMap<>();
        try{
            // 登录操作，返回LoginVO数据类型
            LoginVO vo = doctorService.loginByFace(loginByFaceDTO);
            JWTVO jwtVO = doctorService.selectJwtVO(loginByFaceDTO.getUsername());
            if(vo != null){
                // 返回值，将jwt和LoginVO给put到Result中
                Map<String,String> payload = new HashMap<>();
                payload.put("name",vo.getName());
                payload.put("username",jwtVO.getUsername());
                payload.put("department",vo.getDepartment());
                //生成JWT令牌
                String token = JWTUtil.getToken(payload);
                // 将token封装到VO中
                LoginReturn loginReturn = new LoginReturn();
                loginReturn.setToken(token);
                loginReturn.setDoctor_info(vo);
                return Result.success().code(200).message("登录成功").data(loginReturn);
            }else {
                return Result.error(401,"人脸识别失败");
            }
        } catch (Exception e) {
            return Result.error(404, e.getMessage());
        }
    }

    @PostMapping("/login/sendCode/{contact_number}")
    /**
     * 手机号验证登录--发送验证码
     */
    public Result<?> sendCode(@PathVariable String contact_number) {
        if(doctorService.sendCode(contact_number)){
            return Result.success().code(200).message("验证码已发送");
        }else {
            return Result.error(401,"手机号未注册");
        }
    }

    @PostMapping("/login/phone")
    /**
     * 手机号验证登录--验证验证码和手机号
     */
    public Result<?> loginByPhone(@RequestBody LoginByPhoneDTO loginByPhoneDTO) {
        // jwt封装
        Map<String,Object> map = new HashMap<>();
        try{
            // 登录操作，返回LoginVO数据类型
            LoginVO vo = doctorService.loginByPhone(loginByPhoneDTO);
            JWTVO jwtVO = doctorService.selectJwtVOByPhone(loginByPhoneDTO.getContact_number());
            if(vo != null){
                // 返回值，将jwt和LoginVO给put到Result中
                Map<String,String> payload = new HashMap<>();
                payload.put("name",vo.getName());
                payload.put("username",jwtVO.getUsername());
                payload.put("department",vo.getDepartment());
                //生成JWT令牌
                String token = JWTUtil.getToken(payload);
                // 将token封装到VO中
                LoginReturn loginReturn = new LoginReturn();
                loginReturn.setToken(token);
                loginReturn.setDoctor_info(vo);
                return Result.success().code(200).message("登录成功").data(loginReturn);
            }else {
                return Result.error(401,"验证码错误");
            }
        } catch (Exception e) {
            return Result.error(404, e.getMessage());
        }

    }

    @GetMapping("/profile")
    /**
     * 获取医生个人信息
     */
    public Result<?> getDoctorInfo(@RequestHeader("Authorization") String authorizationHeader) throws IOException {
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            // 获取token值，存储为String类型
            String token = authorizationHeader.substring(7);
            // 传值到Service层处理
            DoctorInfoVO vo = doctorService.selectDoctorInfoByToken(token);
            return Result.success().code(200).message("获取成功").data(vo);
        }else {
            return Result.error(401,"用户未登录或Token无效");
        }
    }

    @PutMapping("/profile")
    /**
     * 修改医生个人信息
     */
    public Result<?> updateDoctorInfo(@RequestBody DoctorUpdateDTO updateDTO,@RequestHeader("Authorization") String authorizationHeader) throws IOException {
        // 获取token值，存储为String类型
        String token = authorizationHeader.substring(7);
        if(doctorService.updateDoctorInfo(updateDTO,token)){
            return Result.success().code(200).message("信息更新成功");
        }else {
            return Result.error(401,"信息更新失败，请检查提交数据");
        }
    }

    @PutMapping("/password")
    /**
     * 修改医生密码
     */
    public Result<?> updatePassword(@RequestBody DoctorPasswordUpdateDTO doctorPasswordUpdateDTO,@RequestHeader("Authorization") String authorizationHeader) throws IOException {
        // 获取token值，存储为String类型
        String token = authorizationHeader.substring(7);
        // 验证密码是否正确
        if(doctorService.selectDoctorByUsernameAndPassword(doctorPasswordUpdateDTO.getCurrent_password(),token)){
            // 设置新密码
            if(doctorService.updateDoctorPassword(doctorPasswordUpdateDTO.getNew_password(),token)){
                return Result.success().code(200).message("密码修改成功，请重新登录");
            }else {
                return Result.error(402,"设置新密码失败，请重试！");
            }
        }else {
            // 密码不正确
            return Result.error(401,"当前密码错误");
        }
    }

    /**
     * TODO 更新人脸数据
     */

    @GetMapping("/permissions")
    /**
     * 获取医生权限
     */
    public Result<?> getDoctorPermissions(@RequestHeader("Authorization") String authorizationHeader) throws IOException {
        // 获取token值，存储为String类型
        String token = authorizationHeader.substring(7);
        int permission_level = doctorService.selectDoctorPermission(token);
        if(permission_level == ConstantUtil.PERMISSIONLEVEL_ERROR){
            return Result.error(401,"权限获取失败");
        }else {
            DoctorPermissionVO vo = new DoctorPermissionVO();
            vo.setPermission_level(permission_level);
            return Result.success().code(200).message("权限获取成功").data(vo);
        }
    }

}
