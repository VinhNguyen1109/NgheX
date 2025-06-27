package com.nghex.exe202.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductAdminDto {
    private Long id;
    private String title;
    private List<String> images;
    private String sellerEmail;
}
