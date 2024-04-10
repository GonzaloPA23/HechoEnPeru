package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
