package com.upc.hechoenperu.dtos;


import com.upc.hechoenperu.entities.Order;
import com.upc.hechoenperu.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private Long id;
    private Integer quantity;
    private BigDecimal subTotal;
    private Order order;
    private Product product;
}
