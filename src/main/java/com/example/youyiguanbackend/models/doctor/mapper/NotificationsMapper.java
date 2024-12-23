package com.example.youyiguanbackend.models.doctor.mapper;

import com.example.youyiguanbackend.models.doctor.model.dto.GetNotificationsListByPageDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.Notifications;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationsMapper {
    int getTotalCountByIdAndStatusAndPriority(GetNotificationsListByPageDTO getNotificationsListByPageDTO);

    List<Notifications> getTotalListByIdAndStatusAndPriority(GetNotificationsListByPageDTO getNotificationsListByPageDTO);

    int markReadById(int notificationId);

    int deleteById(int doctorId);
}
