package com.example.youyiguanbackend.test.doctor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author beetles
 * @date 2024/11/27
 * @Description
 */
public class TokenTest {
    private static String SIGNATURE = "token!@#$%^7890";

    /**
     * 生成token
     * @param map //传入payload
     * @return 返回token
     */
    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,7);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(SIGNATURE)).toString();
    }

    /**
     * 验证token
     * @param token
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }

    /**
     * 获取token中payload
     * @param token
     * @return
     */
    public static DecodedJWT getToken(String token){
        return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("username","test1");
        verify(getToken(map));
        System.out.println("Token: " + getToken(getToken(map)).getToken());
        System.out.println("Header: " + getToken(getToken(map)).getHeader());
        System.out.println("Signature: " + getToken(getToken(map)).getSignature());
        System.out.println("PayLoad: " + getToken(getToken(map)).getPayload());
    }
}
