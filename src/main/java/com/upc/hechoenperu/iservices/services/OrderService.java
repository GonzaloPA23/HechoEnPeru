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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Order order, List<OrderDetail> orderDetails) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderDetail orderDetail : orderDetails){
            Product product = productRepository.findById(orderDetail.getProduct().getId()).orElse(null);
            int quantity = orderDetail.getQuantity();

            assert product != null;
            if (!product.getEnabled() || !product.getAvailability()) {
                throw new IllegalArgumentException("Product is not available: " + product.getName());
            }

            if (product.getStock() == null) {
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
            orderDetailRepository.save(orderDetail);
            Product product = productRepository.findById(orderDetail.getProduct().getId()).orElse(null);
            assert product != null;
            product.setStock(product.getStock() - orderDetail.getQuantity());
            if (product.getStock() == 0) product.setAvailability(false); // If stock is 0, set availability to false
            productRepository.save(product);
        }
    }

    @Override
    public List<OrderDetail> listOrderDetails() {
        return orderDetailRepository.findAll();
    }

    @Override
    public List<OrderDetail> listOrderDetailsById(Long orderId, Long userId) {
        if (orderRepository.findByIdAndUserId(orderId, userId) == null) {
            throw new IllegalArgumentException("Order ID: " + orderId + " does not belong to User ID: " + userId);
        }

        return orderDetailRepository.findByOrderIdAndUserId(orderId, userId);
    }

    @Override
    public List<QuantityProductsByRegionResponseDTO> quantityProductsByRegion() {
        return orderDetailRepository.quantityProductsByRegion();
    }

    @Override
    public List<QuantityProductsByCategoryResponseDTO> quantityProductsByCategory() {
        return orderDetailRepository.quantityProductsByCategory();
    }
}
