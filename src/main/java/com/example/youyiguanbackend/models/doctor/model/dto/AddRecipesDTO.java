package com.example.youyiguanbackend.models.doctor.model.dto;

import lombok.Data;

@Data
public class AddRecipesDTO {
    private int doctor_id;
    private String recipe_name;
    private String ingredients;
    private String paozhi;
    private String usage_instructions;
}
