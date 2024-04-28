package com.upc.hechoenperu.iservices;

import com.upc.hechoenperu.dtos.response.QuantityProductsByCategoryResponseDTO;
import com.upc.hechoenperu.dtos.response.QuantityProductsByRegionResponseDTO;
import com.upc.hechoenperu.entities.Order;
import com.upc.hechoenperu.entities.OrderDetail;

import java.util.List;

public interface IOrderService {
    void insert(Order order, List<OrderDetail> orderDetails);
    List<OrderDetail> listOrderDetails();
    List<OrderDetail> listOrderDetailsById(Long orderId, Long userId);
    List<QuantityProductsByRegionResponseDTO> quantityProductsByRegion();
    List<QuantityProductsByCategoryResponseDTO> quantityProductsByCategory();
}
