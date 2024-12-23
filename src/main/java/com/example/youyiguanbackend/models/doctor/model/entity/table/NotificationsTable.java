package com.example.youyiguanbackend.models.doctor.model.entity.table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("notifications")
public class NotificationsTable implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField("doctor_id")
    private Integer doctor_id;

    @TableField("text")
    private String text;

    @TableField("notification_type")
    private String notification_type;

    @TableField("status")
    private String status;

    @TableField("priority")
    private String priority;

    @TableField("created_at")
    private String created_at;

    @TableField("updated_at")
    private String updated_at;

    @TableField("remarks")
    private String remarks;

    @TableId(value = "notification_id", type = IdType.AUTO)
    private Integer notification_id;
}
