package com.example.youyiguanbackend.models.doctor.service.serviceImpl;

import com.example.youyiguanbackend.common.doctor.Result.Result;
import com.example.youyiguanbackend.common.doctor.Util.GsonUtils;
import com.example.youyiguanbackend.common.doctor.Util.HttpUtil;
import com.example.youyiguanbackend.models.doctor.pojo.Doctor;
import com.example.youyiguanbackend.models.doctor.service.DoctorService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import okhttp3.*;
import org.json.JSONObject;

import java.io.*;

import java.util.HashMap;
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

    // 人脸识别keys
    public static final String API_KEY = "nL0dvdtgHgW9Av262KnaImzr";
    public static final String SECRET_KEY = "RK41beQA07KrOCkeiUW2kb6bIvWPSQDS";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();


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

    /**
     * TODO 验证上传的人脸图片数据的质量,同时将人脸数据存储
     */
    @Override
    public boolean validateFace(String base64String) throws IOException {

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            Map<String, Object> map = new HashMap<>();
            // TODO 人脸识别的人脸组库，用户id,可以通过数据库查询来将id绑定为名字
            map.put("image", base64String);
            map.put("group_id", "group_repeat");
            map.put("user_id", "user1");
            map.put("image_type", "BASE64");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAccessToken();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            // 判断result中error_code的值，如果为0则表示成功，其他则失败   222203为人脸图像已经录入
            // 使用JSONObject解析字符串
            JSONObject responseJson = new JSONObject(result);
            // 获取error_code的值
            int errorCode = responseJson.getInt("error_code");
            // 判断error_code的值
            if (errorCode == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }
}
