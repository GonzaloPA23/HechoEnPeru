package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.request.OrderDetailRequestDTO;
import com.upc.hechoenperu.dtos.request.OrderRequestDTO;
import com.upc.hechoenperu.dtos.response.OrderDetailsResponseDTO;
import com.upc.hechoenperu.dtos.response.QuantityProductsByCategoryResponseDTO;
import com.upc.hechoenperu.dtos.response.QuantityProductsByRegionResponseDTO;
import com.upc.hechoenperu.entities.Order;
import com.upc.hechoenperu.entities.OrderDetail;
import com.upc.hechoenperu.iservices.services.OrderServiceService;
import com.upc.hechoenperu.security.JwtTokenUtil;
import com.upc.hechoenperu.util.DTOConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Order")
@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderServiceService orderService;
    @Autowired
    private DTOConverter dtoConverter;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Create a new order")
    @PostMapping("/order")
    public ResponseEntity<?> insert(@RequestBody OrderRequestDTO orderRequestDTO){
        try {
            Order order = dtoConverter.convertToEntity(orderRequestDTO, Order.class);
            List<OrderDetail> orderDetails = convertToEntityList(orderRequestDTO.getOrderDetails());
            orderService.insert(order, orderDetails);
            return ResponseEntity.ok("Order created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "List all orders")
    @GetMapping("/orders")
    public ResponseEntity<?> listOrderDetails(){
        try {
            List<OrderDetail> orderDetails = orderService.listOrderDetails();
            List<OrderDetailsResponseDTO> orderDetailsResponseDTOS = orderDetails.stream().map(orderDetail -> dtoConverter.convertToDto(orderDetail, OrderDetailsResponseDTO.class)).toList();
            return ResponseEntity.ok(orderDetailsResponseDTOS);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "List all orders by user id and order id")
    @GetMapping("/ordersDetails")
        public ResponseEntity<?> listOrderDetailsById(@Param("orderId") Long orderId, @Param("userId") Long userId){
        try {
            List<OrderDetail> orderDetails = orderService.listOrderDetailsById(orderId, userId);
            List<OrderDetailsResponseDTO> orderDetailsResponseDTOS = orderDetails.stream().map(orderDetail -> dtoConverter.convertToDto(orderDetail, OrderDetailsResponseDTO.class)).toList();
            return ResponseEntity.ok(orderDetailsResponseDTOS);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "List quantity of products by region")
    @GetMapping("/productsQuantityByRegion")
    public ResponseEntity<?> quantityProductsByRegion(){
        try {
            List<QuantityProductsByRegionResponseDTO> quantityProductsByRegionResponseDTOS = orderService.quantityProductsByRegion();
            return ResponseEntity.ok(quantityProductsByRegionResponseDTOS);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "List quantity of products by category")
    @GetMapping("/productsQuantityByCategory")
    public ResponseEntity<?> quantityProductsByCategory(){
        try {
            List<QuantityProductsByCategoryResponseDTO> quantityProductsByCategoryResponseDTOS = orderService.quantityProductsByCategory();
            return ResponseEntity.ok(quantityProductsByCategoryResponseDTOS);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private List<OrderDetail> convertToEntityList(List<OrderDetailRequestDTO> orderDetailRequestDTOS) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailRequestDTO orderDetailRequestDTO : orderDetailRequestDTOS) {
            OrderDetail orderDetail = dtoConverter.convertToEntity(orderDetailRequestDTO, OrderDetail.class);
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }
}
