package com.example.youyiguanbackend.models.doctor.mapper;

import com.example.youyiguanbackend.models.doctor.model.pojo.Doctor;
import com.example.youyiguanbackend.models.doctor.model.pojo.SelectDoctorUserNameAndEmailVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author beetles
 * @date 2024/11/25
 * @Description
 */
@Mapper
public interface DoctorMapper {

    int insertDoctor(Doctor doctor);
    SelectDoctorUserNameAndEmailVO selectDoctorUserNameAndEmail(Doctor doctor);
}
