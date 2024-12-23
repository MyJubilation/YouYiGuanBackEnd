package com.example.youyiguanbackend.models.doctor.model.pojo;


import lombok.Data;

@Data
public class Notifications{
    private int notification_id;
    private int doctor_id;
    private String msg;
    private String notification_type;
    private String status;
    private String priority;
    private String created_at;
    private String updated_at;
    private String remarks;
}
