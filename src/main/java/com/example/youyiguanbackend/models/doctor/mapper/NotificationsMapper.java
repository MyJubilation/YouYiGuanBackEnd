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

    int getUnreadCountById(int doctorId);

    int getMap1ById(int doctorId);

    int getMap2ById(int doctorId);

    int createNotifications(Notifications notifications);
}
