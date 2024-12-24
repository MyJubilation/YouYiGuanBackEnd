package com.example.youyiguanbackend.models.doctor.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.youyiguanbackend.common.doctor.Result.Result;
import com.example.youyiguanbackend.models.doctor.model.dto.GetNotificationsListByPageDTO;
import com.example.youyiguanbackend.models.doctor.model.entity.table.NotificationsTable;
import com.example.youyiguanbackend.models.doctor.model.pojo.GetUnreadCountByIdVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.Notifications;
import com.example.youyiguanbackend.models.doctor.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    @Autowired
    private NotificationsService notificationsService;

    /**
     * 获取通知
     */
    @GetMapping("")
    public Result<?> getPageNotificationsList(@RequestBody GetNotificationsListByPageDTO getNotificationsListByPageDTO) {


        Map<String, Object> obj = new HashMap<>();
        // 查询total数量
        int total = notificationsService.getTotalCountByIdAndStatusAndPriority(getNotificationsListByPageDTO);
        obj.put("total", total);
        // 查询分页结果
        List<Notifications> result = notificationsService.getTotalListByIdAndStatusAndPriority(getNotificationsListByPageDTO);
        obj.put("notifications", result);

        return Result.success(obj);
    }

    /**
     * 标记通知为已读
     */
    @PostMapping("/mark-read/{notification_id}")
    public Result<?> markReadById(@PathVariable int notification_id) {
        int result = notificationsService.markReadById(notification_id);
        if (result == 1) {
            return Result.success().message("通知已标记为已读");
        }else {
            return Result.error(401,"标记已读失败");
        }
    }
    /**
     * 清空该医生所有通知
     */
    @DeleteMapping("/{doctor_id}")
    public Result<?> deleteById(@PathVariable int doctor_id) {
        int result = notificationsService.deleteById(doctor_id);
        if(result != 0) {
            return Result.success().message("所有通知已清空");
        }else {
            return Result.error(401,"通知清空失败");
        }
    }
    /**
     *
     */
    @PostMapping("/create")
    public Result<?> createNotifications(@RequestBody Notifications notifications) {
        // 设置默认值：status为“未读”，priority为“中”，如果remarks为空则设置为默认值“无”
        notifications.setStatus("未读");
        notifications.setPriority("中");
        if(notifications.getRemarks() == null || notifications.getRemarks().equals("")) {
            notifications.setRemarks("无");
        }
        int result = notificationsService.createNotifications(notifications);
        if(result != 0) {
            return Result.success().message("通知已创建");
        }else {
            return Result.error(401,"通知创建失败");
        }
    }

    /**
     * 获取未读通知数量
     */
    @GetMapping("/unread-count/{doctor_id}")
    public Result<?> getUnreadCountById(@PathVariable int doctor_id) {
        GetUnreadCountByIdVO getUnreadCountByIdVO = notificationsService.getUnreadCountById(doctor_id);
        if(getUnreadCountByIdVO != null) {
            return Result.success(getUnreadCountByIdVO);
        }else {
            return Result.error(401,"未读通知统计失败");
        }
    }
}
