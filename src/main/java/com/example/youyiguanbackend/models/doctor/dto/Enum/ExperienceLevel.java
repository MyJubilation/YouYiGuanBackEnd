package com.example.youyiguanbackend.models.doctor.dto.Enum;

/**
 * @author beetles
 * @date 2024/11/20
 * @Description
 */
public enum ExperienceLevel {
    Level1("初级职称(医士、医师/住院医师)"),
    LEVEL2("中级职称(主治医师)"),
    LEVEL3("副高级职称(副主任医师)"),
    LEVEL4("正高级职称(主任医师)");

    private String description;

    ExperienceLevel(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

}
