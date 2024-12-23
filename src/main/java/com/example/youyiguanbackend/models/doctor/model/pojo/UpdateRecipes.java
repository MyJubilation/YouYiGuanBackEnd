package com.example.youyiguanbackend.models.doctor.model.pojo;

import lombok.Data;

@Data
public class UpdateRecipes {
    private String recipe_name;
    private String ingredients;
    private String paozhi;
    private String usage_instructions;
}
