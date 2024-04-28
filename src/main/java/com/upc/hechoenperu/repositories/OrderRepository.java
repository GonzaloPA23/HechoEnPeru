package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // findByIdAndUserId
    Order findByIdAndUserId(Long id, Long userId);
}
