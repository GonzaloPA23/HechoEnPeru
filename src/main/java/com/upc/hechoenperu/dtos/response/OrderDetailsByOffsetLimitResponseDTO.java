package com.upc.hechoenperu.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsByOffsetLimitResponseDTO {
    private Long id;
    private int quantity;
    private double subTotal;
    private Long productId;
}
