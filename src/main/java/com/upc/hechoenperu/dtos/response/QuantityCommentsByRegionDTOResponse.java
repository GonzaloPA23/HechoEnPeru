package com.upc.hechoenperu.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityCommentsByRegionDTOResponse {
    private String regionName;
    private Long quantity;
}
