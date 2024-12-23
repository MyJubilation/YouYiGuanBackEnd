package com.example.youyiguanbackend.models.doctor.mapper;

import com.example.youyiguanbackend.models.doctor.model.dto.AddRecipesDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.GetRecipesListVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.UpdateRecipes;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface RecipesMapper {
    List<GetRecipesListVO> getRecipesList(int doctorId, LocalDateTime dateFrom, LocalDateTime dateTo);

    int addRecipes(AddRecipesDTO addRecipesDTO);

    int updateRecipes(int customRecipeId, UpdateRecipes updateRecipes);

    UpdateRecipes selectUpdateRecipes(int customRecipeId);

    int deleteRecipes(int customRecipeId);
}
