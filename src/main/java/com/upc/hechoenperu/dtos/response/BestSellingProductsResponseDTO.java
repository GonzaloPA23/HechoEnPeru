package com.upc.hechoenperu.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BestSellingProductsResponseDTO {
    private Long total;
    private String productName;
    private String image;
    private BigDecimal price;
    private String regionName;
}
