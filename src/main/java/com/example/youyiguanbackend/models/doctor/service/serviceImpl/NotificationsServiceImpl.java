package com.example.youyiguanbackend.models.doctor.service.serviceImpl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.youyiguanbackend.models.doctor.mapper.NotificationsMapper;
import com.example.youyiguanbackend.models.doctor.model.dto.GetNotificationsListByPageDTO;
import com.example.youyiguanbackend.models.doctor.model.entity.table.NotificationsTable;
import com.example.youyiguanbackend.models.doctor.model.pojo.Notifications;
import com.example.youyiguanbackend.models.doctor.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationsServiceImpl implements NotificationsService {

    @Autowired
    private NotificationsMapper notificationsMapper;

    @Override
    public int getTotalCountByIdAndStatusAndPriority(GetNotificationsListByPageDTO getNotificationsListByPageDTO) {
        return notificationsMapper.getTotalCountByIdAndStatusAndPriority(getNotificationsListByPageDTO);
    }

    @Override
    public List<Notifications> getTotalListByIdAndStatusAndPriority(GetNotificationsListByPageDTO getNotificationsListByPageDTO) {
        getNotificationsListByPageDTO.setPage((getNotificationsListByPageDTO.getPage()-1) * getNotificationsListByPageDTO.getLimit());
        return notificationsMapper.getTotalListByIdAndStatusAndPriority(getNotificationsListByPageDTO);
    }

    @Override
    public int markReadById(int notificationId) {
        return notificationsMapper.markReadById(notificationId);
    }

    @Override
    public int deleteById(int doctorId) {
        return notificationsMapper.deleteById(doctorId);
    }
}
