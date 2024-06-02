package com.upc.hechoenperu.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsByAverageRatingResponseDTO {
    private String rangeAverageRating;
    private Long quantity;
}
