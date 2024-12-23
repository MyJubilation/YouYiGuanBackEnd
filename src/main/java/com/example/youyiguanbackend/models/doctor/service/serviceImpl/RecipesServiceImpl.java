package com.example.youyiguanbackend.models.doctor.service.serviceImpl;

import com.example.youyiguanbackend.models.doctor.mapper.RecipesMapper;
import com.example.youyiguanbackend.models.doctor.model.dto.AddRecipesDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.GetRecipesListVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.UpdateRecipes;
import com.example.youyiguanbackend.models.doctor.service.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipesServiceImpl implements RecipesService {

    @Autowired
    private RecipesMapper recipesMapper;

    @Override
    public List<GetRecipesListVO> getRecipesList(int doctorId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return recipesMapper.getRecipesList(doctorId,dateFrom,dateTo);
    }

    @Override
    public int addRecipes(AddRecipesDTO addRecipesDTO) {
        return recipesMapper.addRecipes(addRecipesDTO);
    }

    @Override
    public UpdateRecipes updateRecipes(int customRecipeId, UpdateRecipes updateRecipes) {
        int result = recipesMapper.updateRecipes(customRecipeId,updateRecipes);
        if(result != 0){
            // 查询并返回
            return recipesMapper.selectUpdateRecipes(customRecipeId);
        }else {
            return null;
        }
    }

    @Override
    public int deleteRecipes(int customRecipeId) {
        return recipesMapper.deleteRecipes(customRecipeId);
    }
}
