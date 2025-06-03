package com.nghex.exe202.service.impl;

import com.nghex.exe202.dto.CategoryDto;
import com.nghex.exe202.entity.Category;
import com.nghex.exe202.repository.CategoryRepository;
import com.nghex.exe202.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public List<Category> getTop10Category() {
        Pageable pageable = PageRequest.of(0, 10);
        return categoryRepository.findTop10ByCategoryIdDesc(pageable);
    }

    @Override
    public List<CategoryDto> getByParentId(Long id) {
        List<Category> level1Categories = categoryRepository.findByParentCategoryId(id);
        return level1Categories.stream()
                .map(this::toDtoRecursive)
                .collect(Collectors.toList());
    }


    @Override
    public List<Category> getByLevel(Integer level) {
        return categoryRepository.findByLevel(level);
    }

    @Override
    public List<Category> getTop10Level3() {
        return categoryRepository.getTop10ByLevel(3);
    }

    public CategoryDto toDtoRecursive(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setLevel(category.getLevel());

        List<Category> children = categoryRepository.findByParentCategoryId(category.getId());
        if (!children.isEmpty()) {
            List<CategoryDto> childDtos = children.stream()
                    .map(this::toDtoRecursive) // đệ quy
                    .collect(Collectors.toList());
            dto.setChildren(childDtos);
        }

        return dto;
    }

}
