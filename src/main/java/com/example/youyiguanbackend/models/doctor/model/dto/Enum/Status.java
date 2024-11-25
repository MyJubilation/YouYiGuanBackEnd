package com.example.youyiguanbackend.models.doctor.model.dto.Enum;

/**
 * @author beetles
 * @date 2024/11/20
 * @Description
 */
public enum Status {
    SUCCESS("激活"),
    ERROR("未激活");

    private String description;
    Status(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
