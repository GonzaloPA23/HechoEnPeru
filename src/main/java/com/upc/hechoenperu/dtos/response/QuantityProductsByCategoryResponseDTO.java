package com.upc.hechoenperu.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityProductsByCategoryResponseDTO {
    private Long quantityProductsSold;
    private String categoryName;
}
