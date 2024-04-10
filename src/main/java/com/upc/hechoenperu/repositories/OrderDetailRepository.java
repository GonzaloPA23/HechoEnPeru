package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{
}
