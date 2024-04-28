package com.upc.hechoenperu.dtos.request;

import com.upc.hechoenperu.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private User user;
    private BigDecimal total;
    private List<OrderDetailRequestDTO> orderDetails;
}
