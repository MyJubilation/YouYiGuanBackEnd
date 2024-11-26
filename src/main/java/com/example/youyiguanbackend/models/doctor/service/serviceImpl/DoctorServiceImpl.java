package com.example.youyiguanbackend.models.doctor.service.serviceImpl;

import com.example.youyiguanbackend.common.doctor.Util.ConstantUtil;
import com.example.youyiguanbackend.common.doctor.Util.GsonUtils;
import com.example.youyiguanbackend.common.doctor.Util.HttpUtil;
import com.example.youyiguanbackend.common.doctor.common.BaseContext;
import com.example.youyiguanbackend.models.doctor.mapper.DoctorMapper;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Department;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.ExperienceLevel;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Gender;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Status;
import com.example.youyiguanbackend.models.doctor.model.dto.LoginDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.RegisterDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.Doctor;
import com.example.youyiguanbackend.models.doctor.model.pojo.LoginVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.RegisterVO;
import com.example.youyiguanbackend.models.doctor.service.DoctorService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import okhttp3.*;
import org.json.JSONObject;

import java.io.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    private DoctorMapper doctorMapper;

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
    public int validateFace(String base64String) throws IOException {

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
            // 判断result中error_code的值，如果为0则表示成功，其他则失败  222203为人脸图像已经录入
            // 使用JSONObject解析字符串
            JSONObject responseJson = new JSONObject(result);
            // 获取error_code的值
            int errorCode = responseJson.getInt("error_code");
            // 判断error_code的值
            if (errorCode == 0) { // 人脸数据注册成功
                // 获取face_token值
                JSONObject data = responseJson.getJSONObject("result");
                String faceToken = data.getString("face_token");
                // 先暂时存入缓存中，后续注册成功才将人脸数据一块输入
                // 将face_token存入缓存
                stringRedisTemplate.opsForValue().set("faceToken", faceToken,1, TimeUnit.HOURS);
                // TODO 如果注册失败则需要将人脸数据从人脸库中删除

                return ConstantUtil.FaceIdSetSuccess;
            } else if (errorCode == 223105) { // 人脸数据已经存在
                return ConstantUtil.FaceIdAlreadyExist;
            }else { // 人脸数据注册失败
                return ConstantUtil.FaceIdSetError;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ConstantUtil.FaceIdSetError;
    }

    /**
     *  用于注册功能的实现
     * @param registerDTO
     * @return
     */
    @Override
    public RegisterVO register(RegisterDTO registerDTO) {
        // 将DTO转换为实体类
        Doctor doctor = new Doctor();
        // 获取face_token并存入实体类中,并存储当前时间到accountCreationDate中,将DTO转换为实体类
        String faceToken = stringRedisTemplate.opsForValue().get("faceToken");
        doctor.setName(registerDTO.getName());
        doctor.setAge(registerDTO.getAge());
        doctor.setHospitalName(registerDTO.getHospital_name());
        doctor.setContactNumber(registerDTO.getContact_number());
        doctor.setEmail(registerDTO.getEmail());
        doctor.setUsername(registerDTO.getUsername());
        doctor.setPassword(registerDTO.getPassword());
        doctor.setFaceImageUrl(registerDTO.getFace_image_ase64());

        doctor.setPermissionLevel(1); // 暂定1为最低等级
        doctor.setAiDiagnosisReviewEnabled(1);

        doctor.setFaceToken(faceToken);
        doctor.setAccountCreationDate(LocalDateTime.now());
        doctor.setStatus(Status.SUCCESS);
        // 判断并传值
        if(registerDTO.getGender().equals("男")){
            doctor.setGender(Gender.MAN);
        }else if(registerDTO.getGender().equals("女")){
            doctor.setGender(Gender.WOMAN);
        }else {
            return null;
        }
        for(ExperienceLevel e : ExperienceLevel.values()){
            if(e.getDescription().toString().equals(registerDTO.getExperience_level())){
                doctor.setExperienceLevel(e);
            }
        }
        for(Department d : Department.values()){
            if(d.getDescription().toString().equals(registerDTO.getDepartment())){
                doctor.setDepartment(d);
            }
        }
        // 如果有任何值错误，则返回false
        if(doctor.getExperienceLevel() == null || doctor.getDepartment() == null){
            return null;
        }
        // 判断数据库是否已经有数据，如果有返回null
        if(doctorMapper.selectDoctorUserNameAndEmail(doctor) != null){
            return null;
        }
        //  存储到数据库
        RegisterVO registerVO = new RegisterVO();
        doctorMapper.insertDoctor(doctor);
        registerVO.setDoctorId(doctor.getDoctor_id());
        registerVO.setStatus("激活");
        // 将faceToken从redis中删除
        stringRedisTemplate.delete("faceToken");
        //  返回逻辑实现
        return registerVO;
    }

    /**
     * 医生用户名密码登录
     */
    @Override
    public LoginVO loginByUsername(LoginDTO loginDTO) {
        LoginVO vo = doctorMapper.selectDoctorByUsernameAndEmail(loginDTO);
        if(vo != null){
            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();
            // // 创建一个DateTimeFormatter对象，指定要格式化的样式
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // // 使用formatter格式化LocalDateTime对象
            // String formattedDate = now.format(formatter);
            // 将现在时间存入返回VO中
            vo.setLast_login(now);
            // 将lastLogin存入数据库中
            doctorMapper.updateLastLogin(vo);
            return vo;
        }else {
            return null;
        }
    }

    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
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
