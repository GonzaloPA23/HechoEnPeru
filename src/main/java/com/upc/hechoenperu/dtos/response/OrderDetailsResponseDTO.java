package com.upc.hechoenperu.dtos.response;

import com.upc.hechoenperu.entities.Order;
import com.upc.hechoenperu.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsResponseDTO {
    private Long id;
    private Order order;
    private Product product;
    private Integer quantity;
    private BigDecimal subTotal;
}
