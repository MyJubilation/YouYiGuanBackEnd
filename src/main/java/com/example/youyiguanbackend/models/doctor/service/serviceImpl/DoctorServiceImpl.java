package com.example.youyiguanbackend.models.doctor.service.serviceImpl;

import com.example.youyiguanbackend.common.doctor.Result.Result;
import com.example.youyiguanbackend.models.doctor.pojo.Doctor;
import com.example.youyiguanbackend.models.doctor.service.DoctorService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author beetles
 * @date 2024/11/20
 * @Description
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    /**
     * 获取验证码文本并发送验证码到对应手机号
     */
    public boolean sendCode(String phoneNumber) {
        // 获取验证码
        String code = defaultKaptcha.createText();
        // 存储验证码到redis,保存时间为15分钟
        stringRedisTemplate.opsForValue().set("code", code,15, TimeUnit.MINUTES);
        if(!phoneNumber.isEmpty()){
            // 存储手机号到redis,保存时间为15分钟
            stringRedisTemplate.opsForValue().set("phoneNumber",phoneNumber,15, TimeUnit.MINUTES);
        }
        // TODO 发送验证码到手机号

        return true;
    }

    /**
     * 验证用户传来的验证码
     */
    @Override
    public boolean checkVCode(String phoneNumber, String vCode) {
        if(phoneNumber.equals(stringRedisTemplate.opsForValue().get("phoneNumber")) && vCode.equals(stringRedisTemplate.opsForValue().get("code"))){
            return true;
        }else {
            return false;
        }
    }


}
