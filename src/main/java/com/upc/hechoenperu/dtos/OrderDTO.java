package com.upc.hechoenperu.dtos;

import com.upc.hechoenperu.entities.OrderDetail;
import com.upc.hechoenperu.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private Instant dateCreated;
    private BigDecimal total;
    private User users;
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();
}
