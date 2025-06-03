package com.nghex.exe202.service;

import com.nghex.exe202.dto.CategoryDto;
import com.nghex.exe202.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getTop10Category();

    List<CategoryDto> getByParentId(Long id);

    List<Category> getByLevel(Integer level);


    List<Category> getTop10Level3();
}
