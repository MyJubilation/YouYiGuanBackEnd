package com.example.youyiguanbackend.models.doctor.dto.Enum;

/**
 *
 * @author beetles
 * @date 2024/11/20
 * @Description 
 */
public enum Gender {
     MAN("男"),
     WOMAN("女");

    private final String description;

    Gender(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
