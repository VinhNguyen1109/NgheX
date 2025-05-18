package com.nghex.exe202.request;

import lombok.Data;

@Data
public class CreateCategoryRequest {
    private String parentCategoryId;
    private int level;
    private String name;
    private String categoryId;
}
