package com.example.youyiguanbackend.models.doctor.model.dto;

import lombok.Data;

@Data
public class GetNotificationsListByPageDTO {
    private static final int PAGEDEFAULT = 1;
    private static final int LIMITDEFAULT = 10;

    private int doctor_id;
    private String status;
    private String priority;
    private int page = PAGEDEFAULT;
    private int limit = LIMITDEFAULT;
}
