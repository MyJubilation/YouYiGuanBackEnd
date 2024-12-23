package com.example.youyiguanbackend.models.doctor.model.pojo;

import lombok.Data;

@Data
public class GetRecipesListVO {
    private int custom_recipe_id;
    private String recipe_name;
    private String ingredients;
    private String paozhi;
    private String usage_instructions;
    private String created_date;
}
