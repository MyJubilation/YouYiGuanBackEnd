package com.example.youyiguanbackend.models.doctor.service;

import com.example.youyiguanbackend.models.doctor.model.dto.DoctorUpdateDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.LoginByFaceDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.LoginDTO;
import com.example.youyiguanbackend.models.doctor.model.dto.RegisterDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.*;

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

    LoginVO loginByUsername(LoginDTO loginDTO);

    LoginVO loginByFace(LoginByFaceDTO loginByFaceDTO);

    LoginVO loginByPhone(LoginByPhoneDTO loginByPhoneDTO);

    DoctorInfoVO selectDoctorInfoByToken(String token) throws IOException;

    JWTVO selectJwtVO(String username);

    JWTVO selectJwtVOByPhone(String contactNumber);

    boolean updateDoctorInfo(DoctorUpdateDTO updateDTO,String authorizationHeader) throws IOException;

    boolean selectDoctorByUsernameAndPassword(String currentPassword, String token) throws IOException;

    boolean updateDoctorPassword(String newPassword, String token) throws IOException;

    int selectDoctorPermission(String token) throws IOException;
}
