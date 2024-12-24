package com.example.youyiguanbackend.models.doctor.model.pojo;

import lombok.Data;

import java.util.Map;

@Data
public class GetUnreadCountByIdVO {
    private int doctorId;
    private int unreadCount;
    private Map<String, Integer> breakdown;
    private String lastCheckedAt;
}
