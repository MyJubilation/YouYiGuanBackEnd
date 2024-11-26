package com.example.youyiguanbackend.models.doctor.mapper;

import com.example.youyiguanbackend.models.doctor.model.dto.LoginDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.Doctor;
import com.example.youyiguanbackend.models.doctor.model.pojo.LoginVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.SelectDoctorUserNameAndEmailVO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * @author beetles
 * @date 2024/11/25
 * @Description
 */
@Mapper
public interface DoctorMapper {

    int insertDoctor(Doctor doctor);
    // 用于判断唯一值 用户名（username） 和 邮箱（email） 是否存在
    SelectDoctorUserNameAndEmailVO selectDoctorUserNameAndEmail(Doctor doctor);

    LoginVO selectDoctorByUsernameAndEmail(LoginDTO loginDTO);

    void updateLastLogin(LoginVO loginVO);
}
