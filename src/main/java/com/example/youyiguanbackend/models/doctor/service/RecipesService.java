package com.example.youyiguanbackend.models.doctor.service;

import com.example.youyiguanbackend.models.doctor.model.dto.AddRecipesDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.GetRecipesListVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.UpdateRecipes;

import java.time.LocalDateTime;
import java.util.List;

public interface RecipesService {
    List<GetRecipesListVO> getRecipesList(int doctorId, LocalDateTime dateFrom, LocalDateTime dateTo);

    int addRecipes(AddRecipesDTO addRecipesDTO);

    UpdateRecipes updateRecipes(int customRecipeId, UpdateRecipes updateRecipes);

    int deleteRecipes(int customRecipeId);
}
