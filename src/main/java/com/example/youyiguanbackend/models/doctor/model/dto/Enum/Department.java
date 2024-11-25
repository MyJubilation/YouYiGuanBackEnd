package com.example.youyiguanbackend.models.doctor.model.dto.Enum;

/**
 * @author beetles
 * @date 2024/11/20
 * @Description
 */
public enum Department {
    TCMINTERNALMEDICINE("中医内科"),
    TCMSURGERY("中医外科"),
    TCMQYNECOLOGY("中医妇科"),
    TCMPEDIATRICS("中医儿科"),
    TCMOPHTHALMOLOGY("中医五官科"),
    TCMORTHOPEDICSANDTRAUMATOLOGY("中医骨伤科"),
    TCMHEALTHCAREDEPARTMENT("中医保健科"),
    TCMACUPUNCTURETUINA("针灸推拿科");

    private String description;
    Department(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
