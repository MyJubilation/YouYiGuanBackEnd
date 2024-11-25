package com.example.youyiguanbackend.models.doctor.service;

import com.example.youyiguanbackend.models.doctor.model.dto.RegisterDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.RegisterVO;

import java.io.IOException;

/**
 * @author beetles
 * @date 2024/11/20
 * @Description
 */
public interface DoctorService {

    boolean sendCode(String phoneNumber);

    boolean checkVCode(String phoneNumber, String vCode);

    int validateFace(String base64String) throws IOException;

    RegisterVO register(RegisterDTO registerDTO);
}
