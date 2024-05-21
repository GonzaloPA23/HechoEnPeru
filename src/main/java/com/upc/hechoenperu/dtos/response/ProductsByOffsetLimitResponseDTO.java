package com.upc.hechoenperu.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsByOffsetLimitResponseDTO {
    private Long id;
    private String name;
    private String categoryName;
    private String regionName;
    private String localCraftsmanName;
    private BigDecimal price;
    private Integer stock;
    private Float averageRating;
    private Boolean enabled;
    private Boolean availability;
}
