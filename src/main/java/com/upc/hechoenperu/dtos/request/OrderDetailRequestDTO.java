package com.upc.hechoenperu.dtos.request;

import com.upc.hechoenperu.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRequestDTO {
    private Product product;
    private Integer quantity;
    private BigDecimal subTotal;
}
