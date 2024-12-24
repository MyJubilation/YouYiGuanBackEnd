package com.example.youyiguanbackend.models.doctor.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.youyiguanbackend.models.doctor.model.dto.GetNotificationsListByPageDTO;
import com.example.youyiguanbackend.models.doctor.model.entity.table.NotificationsTable;
import com.example.youyiguanbackend.models.doctor.model.pojo.GetUnreadCountByIdVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.Notifications;

import java.util.List;

public interface NotificationsService {

    int getTotalCountByIdAndStatusAndPriority(GetNotificationsListByPageDTO getNotificationsListByPageDTO);

    List<Notifications> getTotalListByIdAndStatusAndPriority(GetNotificationsListByPageDTO getNotificationsListByPageDTO);

    int markReadById(int notificationId);

    int deleteById(int doctorId);

    GetUnreadCountByIdVO getUnreadCountById(int doctorId);

    int createNotifications(Notifications notifications);
}
