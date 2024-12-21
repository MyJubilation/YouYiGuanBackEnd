package com.example.youyiguanbackend.test.doctor;

import com.example.youyiguanbackend.common.doctor.Util.GsonUtils;
import com.example.youyiguanbackend.common.doctor.Util.HttpUtil;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author beetles
 * @date 2024/11/22
 * @Description
 */

public class Test1 {
    public static final String base64String = "";

    // 人脸识别keys
    public static final String API_KEY = "nL0dvdtgHgW9Av262KnaImzr";
    public static final String SECRET_KEY = "RK41beQA07KrOCkeiUW2kb6bIvWPSQDS";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public static String add() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            Map<String, Object> selectFaceDB = new HashMap<>();
            selectFaceDB.put("image", base64String);
            selectFaceDB.put("group_id", "doctor");
            selectFaceDB.put("image_type", "BASE64");

            String param = GsonUtils.toJson(selectFaceDB);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = getAccessToken();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Test1.add();
    }

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
        // test
    }
}
