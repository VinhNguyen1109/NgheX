package com.nghex.exe202.controller;

import com.nghex.exe202.dto.CategoryDto;
import com.nghex.exe202.entity.Category;
import com.nghex.exe202.entity.Deal;
import com.nghex.exe202.service.CategoryService;
import com.nghex.exe202.service.HomeCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getAllByLevel/{level}")
    public ResponseEntity<List<Category>> getAllByLevel(@PathVariable Integer level){
        List<Category> data = categoryService.getByLevel(level);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/getAllByParentId/{id}")
    public ResponseEntity<List<CategoryDto>> getAllByParentId(@PathVariable Long id){
        List<CategoryDto> data = categoryService.getByParentId(id);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/getTop10Level3")
    public ResponseEntity<List<Category>> getTop10Level3(){
        List<Category> data = categoryService.getTop10Level3();
        return ResponseEntity.ok(data);
    }

}
