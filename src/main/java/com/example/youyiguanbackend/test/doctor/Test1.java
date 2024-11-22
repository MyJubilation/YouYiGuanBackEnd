package com.example.youyiguanbackend.test.doctor;

import com.example.youyiguanbackend.common.doctor.Util.GsonUtils;
import com.example.youyiguanbackend.common.doctor.Util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author beetles
 * @date 2024/11/22
 * @Description
 */

public class Test1 {
    public static String add() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", "b9bce9723b53bc39dbcee1e6ad476a78");
            map.put("group_id", "group_repeat");
            map.put("user_id", "user1");
            map.put("image_type", "FACE_TOKEN");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.a9fccb8b904a84bd4e9e72c50df48cfd.2592000.1734875822.282335-116351448";

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
}
