package com.example.youyiguanbackend.models.doctor.service.serviceImpl;

import com.example.youyiguanbackend.common.doctor.Util.ConstantUtil;
import com.example.youyiguanbackend.common.doctor.Util.GsonUtils;
import com.example.youyiguanbackend.common.doctor.Util.HttpUtil;
import com.example.youyiguanbackend.common.doctor.common.BaseContext;
import com.example.youyiguanbackend.models.doctor.mapper.DoctorMapper;
import com.example.youyiguanbackend.models.doctor.model.dto.DoctorUpdateDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Department;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.ExperienceLevel;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Gender;
import com.example.youyiguanbackend.models.doctor.model.dto.Enum.Status;
import com.example.youyiguanbackend.models.doctor.model.dto.LoginByFaceDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.LoginDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.RegisterDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.*;
import com.example.youyiguanbackend.models.doctor.service.DoctorService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import okhttp3.*;
import org.json.JSONObject;

import java.io.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
     * 验证上传的人脸图片数据的质量,同时将人脸数据存储
     */
    @Override
    public int validateFace(String base64String) throws IOException {

        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            // 获取自增id，仅用于人脸库存储，不用于后端查询,可以通过数据库查询来将id绑定为名字
            if (stringRedisTemplate.opsForValue().get("faceDatabaseId") == null) {
                stringRedisTemplate.opsForValue().set("faceDatabaseId", "1");
            } else {
                // 查询当前base64String对应faceToken是否已经存在人脸库中
                String urlSelect = "https://aip.baidubce.com/rest/2.0/face/v3/search";
                Map<String, Object> selectFaceDB = new HashMap<>();
                selectFaceDB.put("image", base64String);
                selectFaceDB.put("group_id_list", "doctor");
                selectFaceDB.put("image_type", "BASE64");
                String param = GsonUtils.toJson(selectFaceDB);

                String accessTokenSelect = getAccessToken();

                String resultSelect = HttpUtil.post(urlSelect, accessTokenSelect, "application/json", param);
                /**
                 * {
                 *   "error_code" : 0,
                 *   "error_msg" : "SUCCESS",
                 *   "log_id" : 3937975327,
                 *   "timestamp" : 1732627058,
                 *   "cached" : 0,
                 *   "result" : {
                 *     "face_token" : "b9bce9723b53bc39dbcee1e6ad476a78",
                 *     "user_list" : [ {
                 *       "group_id" : "doctor",
                 *       "user_id" : "16",
                 *       "user_info" : "",
                 *       "score" : 100
                 *     } ]
                 *   }
                 * }
                 */
                JSONObject result = new JSONObject(resultSelect);
                if(result.getInt("error_code") == 0){
                    // 获取faceToken
                    JSONObject data = result.getJSONObject("result");
                    String faceToken = data.getString("face_token");
                    if(doctorMapper.selectFaceToken(faceToken)!= null){
                        // 存在faceToken
                        return ConstantUtil.FaceIdAlreadyExist;
                    }
                    // 查询数据库是否存在
                }else {
                    return ConstantUtil.FaceIdSetError;
                }

                // 将获取的字符串转换为整数
                int currentId = Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get("faceDatabaseId")));
                // 进行数值加法
                int newId = currentId + 1;
                // 将结果转换回字符串并设置回Redis
                stringRedisTemplate.opsForValue().set("faceDatabaseId", String.valueOf(newId));
            }
            Map<String, Object> map = new HashMap<>();
            // 人脸识别的人脸组库，用户id
            map.put("image", base64String);
            map.put("group_id", "doctor");
            map.put("user_id", stringRedisTemplate.opsForValue().get("faceDatabaseId"));
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
     * jwt返回值的查询
     */
    @Override
    public JWTVO selectJwtVO(String username) {
        return doctorMapper.selectJwtVO(username);
    }
    @Override
    public JWTVO selectJwtVOByPhone(String contactNumber) {
        return doctorMapper.selectJwtVOByPhone(contactNumber);
    }

    /**
     * 医生用户名密码登录
     */
    @Override
    public LoginVO loginByUsername(LoginDTO loginDTO) {
        LoginVO vo = doctorMapper.selectDoctorByUsernameAndPassword(loginDTO.getUsername(),loginDTO.getPassword());
        if(vo != null){
            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();
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
     * 医生人脸登录
     */
    @Override
    public LoginVO loginByFace(LoginByFaceDTO loginByFaceDTO) {
        // 人脸检测，获取faceToken
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", loginByFaceDTO.getFace_image_base64());
            map.put("image_type", "BASE64");
            String param = GsonUtils.toJson(map);
            String accessToken = getAccessToken();
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            // 判断result中error_code的值，如果为0则表示成功，其他则失败
            // 使用JSONObject解析字符串
            JSONObject responseJson = new JSONObject(result);
            /**
             * 返回的result的json为：
             * {
             *   "result" : {
             *     "face_num" : 1,
             *     "face_list" : [ {
             *       "angle" : {
             *         "roll" : -2.39,
             *         "pitch" : 8.54,
             *         "yaw" : -2.87
             *       },
             *       "face_token" : "b9bce9723b53bc39dbcee1e6ad476a78",
             *       "location" : {
             *         "top" : 495.49,
             *         "left" : 311.27,
             *         "rotation" : 0,
             *         "width" : 474,
             *         "height" : 434
             *       },
             *       "face_probability" : 1
             *     } ]
             *   },
             *   "log_id" : 3937936291,
             *   "error_msg" : "SUCCESS",
             *   "cached" : 0,
             *   "error_code" : 0,
             *   "timestamp" : 1732622092
             * }
             */
            // 获取error_code的值
            int errorCode = responseJson.getInt("error_code");
            // 判断error_code的值
            if (errorCode == 0) { // 人脸数据注册成功
                // 获取face_token值
                JSONObject dataResult = responseJson.getJSONObject("result");
                JSONArray faceList = dataResult.getJSONArray("face_list");
                JSONObject firstFace = faceList.getJSONObject(0);
                String faceToken = firstFace.getString("face_token");
                // 将faceToken与username在数据库中查询，返回值LoginVO
                LoginByFaceVO vo = new LoginByFaceVO();
                vo.setFaceToken(faceToken);
                vo.setUsername(loginByFaceDTO.getUsername());
                LoginVO loginVO = doctorMapper.selectDoctorByFaceAndUsername(vo);
                if(loginVO != null){
                    // 将最新时间存入loginVO
                    // 获取当前时间
                    LocalDateTime now = LocalDateTime.now();
                    // 将现在时间存入返回VO中
                    loginVO.setLast_login(now);
                    // 将lastLogin存入数据库中
                    doctorMapper.updateLastLogin(loginVO);
                    return loginVO;
                }else {
                    return null;
                }
            }else { // 人脸数据注册失败
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 医生手机号登录
     */
    @Override
    public LoginVO loginByPhone(LoginByPhoneDTO loginByPhoneDTO) {
        // 判断手机号和验证码是否正确
        if(Objects.equals(stringRedisTemplate.opsForValue().get("code"), loginByPhoneDTO.getVerification_code()) &&
                Objects.equals(stringRedisTemplate.opsForValue().get("phoneNumber"), loginByPhoneDTO.getContact_number())){
            // 依照手机号进行查询
            LoginVO vo = doctorMapper.selectDoctorByPhone(loginByPhoneDTO);
            if(vo != null){
                // 获取当前时间
                LocalDateTime now = LocalDateTime.now();
                // 将现在时间存入返回VO中
                vo.setLast_login(now);
                // 将lastLogin存入数据库中
                doctorMapper.updateLastLogin(vo);
                return vo;
            }else {
                return null;
            }
        }else {
            return null;
        }

    }

    /**
     * 医生个人信息获取
     */
    @Override
    public DoctorInfoVO selectDoctorInfoByToken(String token) throws IOException {
        // 解析token中的username
        String username = getUserNameByToken(token);
        // 数据库查询值
        DoctorInfoVO vo = doctorMapper.selectDoctorInfoByUsernameAndDepartment(username);
        if(vo != null){
            return vo;
        }else {
            return null;
        }
    }

    /**
     * 医生个人信息修改
     */
    @Override
    public boolean updateDoctorInfo(DoctorUpdateDTO updateDTO,String token) throws IOException {
        // 解析token中的username
        String username = getUserNameByToken(token);
        // 判断传回数据中的contact_phone是否为已经存在的值
        String updateUsername = doctorMapper.selectDoctorByContactPhone(updateDTO.getContact_number());
        if(updateUsername != username && updateUsername != null){
            return false;
        }
        //将值赋给VO
        DoctorUpdateVO updateVO = new DoctorUpdateVO();
        updateVO.setUsername(username);
        updateVO.setName(updateDTO.getName());
        updateVO.setAge(updateDTO.getAge());
        updateVO.setHospital_name(updateDTO.getHospital_name());
        updateVO.setContact_number(updateDTO.getContact_number());
        updateVO.setEmail(updateDTO.getEmail());
        // 判断并传值
        if(updateDTO.getGender().equals("男")){
            updateVO.setGender(Gender.MAN);
        }else if(updateDTO.getGender().equals("女")){
            updateVO.setGender(Gender.WOMAN);
        }else {
            return false;
        }
        for(ExperienceLevel e : ExperienceLevel.values()){
            if(e.getDescription().toString().equals(updateDTO.getExperience_level())){
                updateVO.setExperience_level(e);
            }
        }
        for(Department d : Department.values()){
            if(d.getDescription().toString().equals(updateDTO.getDepartment())){
                updateVO.setDepartment(d);
            }
        }
        // 如果有任何值错误，则返回false
        if(updateVO.getExperience_level() == null || updateVO.getDepartment() == null){
            return false;
        }
        return doctorMapper.updateDoctorInfo(updateVO);
    }

    /**
     * 验证旧密码是否正确并设置新密码
     */
    @Override
    public boolean selectDoctorByUsernameAndPassword(String currentPassword, String token) throws IOException {
        // 解析token中的username
        String username = getUserNameByToken(token);
        return doctorMapper.selectDoctorByUsernameAndPassword(username, currentPassword) != null;
    }
    @Override
    public boolean updateDoctorPassword(String password, String token) throws IOException {
        // 解析token中的username
        String username = getUserNameByToken(token);
        return doctorMapper.updateDoctorPassword(username,password);
    }

    /**
     * 获取医生权限值
     */
    @Override
    public int selectDoctorPermission(String token) throws IOException {
        // 解析token中的username
        String username = getUserNameByToken(token);
        // 获取医生权限值，失败则返回PERMISSION_ERROR常量
        // 权限值默认为1，不可能位空
        return doctorMapper.selectDoctorPermission(username);
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
