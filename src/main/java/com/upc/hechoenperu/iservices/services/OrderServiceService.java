package com.upc.hechoenperu.iservices.services;

import com.upc.hechoenperu.dtos.response.QuantityProductsByCategoryResponseDTO;
import com.upc.hechoenperu.dtos.response.QuantityProductsByRegionResponseDTO;
import com.upc.hechoenperu.entities.Order;
import com.upc.hechoenperu.entities.OrderDetail;
import com.upc.hechoenperu.entities.Product;
import com.upc.hechoenperu.iservices.IOrderService;
import com.upc.hechoenperu.repositories.OrderDetailRepository;
import com.upc.hechoenperu.repositories.OrderRepository;
import com.upc.hechoenperu.repositories.ProductRepository;
import com.upc.hechoenperu.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailService;
    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Order order, List<OrderDetail> orderDetails) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderDetail orderDetail : orderDetails){
            Product product = productRepository.findById(orderDetail.getProduct().getId()).orElse(null);
            int quantity = orderDetail.getQuantity();

            if (product == null || product.getStock() == null) {
                throw new IllegalArgumentException("Product stock is null for product with ID: " + orderDetail.getProduct().getId());
            }

            if(product.getStock() < quantity){
                throw new IllegalArgumentException("Stock is not enough for product: " + product.getName());
            }

            BigDecimal subtotal = product.getPrice().multiply(new BigDecimal(quantity));
            orderDetail.setSubTotal(subtotal);
            total = total.add(subtotal);
        }
        order.setTotal(total);
        orderRepository.save(order);
        for (OrderDetail orderDetail : orderDetails){
            orderDetail.setOrder(order);
            orderDetailService.save(orderDetail);
            Product product = productRepository.findById(orderDetail.getProduct().getId()).orElse(null);
            assert product != null;
            product.setStock(product.getStock() - orderDetail.getQuantity());
            productRepository.save(product);
        }
    }

    @Override
    public List<OrderDetail> listOrderDetails() {
        return orderDetailService.findAll();
    }

    @Override
    public List<OrderDetail> listOrderDetailsById(Long orderId, Long userId) {
        if (orderRepository.findByIdAndUserId(orderId, userId) == null) {
            throw new IllegalArgumentException("Order ID: " + orderId + " does not belong to User ID: " + userId);
        }

        return orderDetailService.findByOrderIdAndUserId(orderId, userId);
    }

    @Override
    public List<QuantityProductsByRegionResponseDTO> quantityProductsByRegion() {
        return orderDetailService.quantityProductsByRegion();
    }

    @Override
    public List<QuantityProductsByCategoryResponseDTO> quantityProductsByCategory() {
        return orderDetailService.quantityProductsByCategory();
    }
}
