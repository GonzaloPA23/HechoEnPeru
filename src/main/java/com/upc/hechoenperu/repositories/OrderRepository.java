package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // findByIdAndUserId
    Order findByIdAndUserId(Long id, Long userId);
    // value = "SELECT * FROM orders OFFSET :offset LIMIT :limit", nativeQuery = true
    @Query("SELECT o FROM Order o")
    List<Order> listOrdersByPageModeAdmin(Pageable pageable);
    List<Order> listOrdersByUserId(Long userId, Pageable pageable);
}
