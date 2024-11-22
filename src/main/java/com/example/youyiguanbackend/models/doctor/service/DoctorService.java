package com.example.youyiguanbackend.models.doctor.service;

import com.example.youyiguanbackend.common.doctor.Result.Result;
import com.example.youyiguanbackend.models.doctor.pojo.Doctor;

import java.util.Map;

/**
 * @author beetles
 * @date 2024/11/20
 * @Description
 */
public interface DoctorService {

    boolean sendCode(String phoneNumber);

    boolean checkVCode(String phoneNumber, String vCode);
}
