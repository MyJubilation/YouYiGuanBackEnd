package com.example.youyiguanbackend.models.doctor.controller;

import com.example.youyiguanbackend.common.doctor.Result.Result;
import com.example.youyiguanbackend.models.doctor.model.dto.AddRecipesDTO;
import com.example.youyiguanbackend.models.doctor.model.pojo.GetRecipesListVO;
import com.example.youyiguanbackend.models.doctor.model.pojo.UpdateRecipes;
import com.example.youyiguanbackend.models.doctor.service.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 处方功能接口
 */
@RestController
@RequestMapping("/api/recipes")
public class RecipesController {

    @Autowired
    private RecipesService recipesService;

    /**
     *  查看处方列表
     */
    @GetMapping("")
    public Result<?> getRecipesList(@RequestParam(value = "doctor_id") int doctor_id,
                                 @RequestParam(value = "date_from", required = false) String Bdate_from,
                                 @RequestParam(value = "date_to", required = false) String Bdate_to) {
        LocalDateTime date_from = null;
        LocalDateTime date_to = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(Bdate_from != null){
            LocalDate localDateFrom = LocalDate.parse(Bdate_from, formatter);
            date_from = localDateFrom.atStartOfDay(); // 将LocalDate转换为LocalDateTime，时间为00:00:00
        }
        if(Bdate_to != null){
            LocalDate localDateTo = LocalDate.parse(Bdate_to, formatter);
            date_to = localDateTo.atStartOfDay().plusDays(1).minusNanos(1); // 将LocalDate转换为LocalDateTime，时间为23:59:59.999999999
        }
        List<GetRecipesListVO> getRecipesListVO = recipesService.getRecipesList(doctor_id,date_from,date_to);
        if(!getRecipesListVO.isEmpty()){
            return Result.success(getRecipesListVO);
        }else {
            return Result.error(401,"未找到相关处方记录");
        }
    }

    /**
     * 新建处方
     */
    @PostMapping("/custom")
    public Result<?> addRecipes(@RequestBody AddRecipesDTO addRecipesDTO) {
        int result = recipesService.addRecipes(addRecipesDTO);
        if(result != 0){
            return Result.success().message("处方创建成功");
        }else {
            return Result.error(401,"创建处方失败，请检查输入数据");
        }
    }

    /**
     * 修改处方
     */
    @PutMapping("/custom/{custom_recipe_id}")
    public Result<?> updateRecipes(@PathVariable int custom_recipe_id,
                                   @RequestBody UpdateRecipes updateRecipes) {
        UpdateRecipes result = recipesService.updateRecipes(custom_recipe_id,updateRecipes);
        if(result != null){
            return Result.success(result);
        }else {
            return Result.error(401,"修改处方失败，处方不存在");
        }
    }

    /**
     * 删除处方
     */
    @DeleteMapping("/custom/{custom_recipe_id}")
    public Result<?> deleteRecipes(@PathVariable int custom_recipe_id) {
        int result = recipesService.deleteRecipes(custom_recipe_id);
        if(result != 0){
            return Result.success().message("处方删除成功");
        }else {
            return Result.error(401,"删除处方失败，处方不存在或权限不足");
        }
    }
}
