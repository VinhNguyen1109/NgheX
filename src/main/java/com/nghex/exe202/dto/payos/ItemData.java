package com.nghex.exe202.dto.payos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemData {
    @NonNull
    private String name;
    @NonNull
    private Integer quantity;
    @NonNull
    private Integer price;
}
