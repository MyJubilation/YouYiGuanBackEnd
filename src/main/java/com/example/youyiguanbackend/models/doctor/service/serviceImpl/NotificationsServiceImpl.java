package com.example.youyiguanbackend.models.doctor.service.serviceImpl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.youyiguanbackend.models.doctor.mapper.NotificationsMapper;
import com.example.youyiguanbackend.models.doctor.model.dto.GetNotificationsListByPageDTO;
import com.example.youyiguanbackend.models.doctor.model.entity.table.NotificationsTable;
import com.example.youyiguanbackend.models.doctor.model.pojo.GetUnreadCountByIdVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.Notifications;
import com.example.youyiguanbackend.models.doctor.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public GetUnreadCountByIdVO getUnreadCountById(int doctorId) {
        GetUnreadCountByIdVO vo = new GetUnreadCountByIdVO();
        vo.setDoctorId(doctorId);
        // 查询未读数量
        int unread_count = notificationsMapper.getUnreadCountById(doctorId);
        vo.setUnreadCount(unread_count);
        // doctor_id
        Map<String,Integer> map = new HashMap<>();
        int map1 = notificationsMapper.getMap1ById(doctorId);
        int map2 = notificationsMapper.getMap2ById(doctorId);
        map.put("待审核诊断",map1);
        map.put("排队病人提醒",map2);
        vo.setBreakdown(map);
        // 当前时间存储
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 创建一个格式化器，定义你想要的日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 使用格式化器将 LocalDateTime 转换为 String
        String formattedDateTime = now.format(formatter);
        vo.setLastCheckedAt(formattedDateTime);
        return vo;
    }

    @Override
    public int createNotifications(Notifications notifications) {
        return notificationsMapper.createNotifications(notifications);
    }
}
