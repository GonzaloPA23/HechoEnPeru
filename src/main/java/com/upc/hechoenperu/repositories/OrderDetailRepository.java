package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.dtos.response.QuantityProductsByCategoryResponseDTO;
import com.upc.hechoenperu.dtos.response.QuantityProductsByRegionResponseDTO;
import com.upc.hechoenperu.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    // SELECT * FROM order_details
    //    JOIN orders o on o.id = order_details.orders_id
    //    JOIN users u on o.users_id = u.id
    //    JOIN products p on p.id = order_details.products_id
    //WHERE o.id = 9 AND u.id = 4;

    @Query("SELECT od FROM OrderDetail od JOIN od.order o JOIN o.user u JOIN od.product p WHERE o.id = :orderId AND u.id = :userId")
    List<OrderDetail> findByOrderIdAndUserId(@Param("orderId") Long orderId, @Param("userId") Long userId);

    // SELECT SUM(quantity), r.name FROM order_details
    //    JOIN products p on order_details.products_id = p.id
    //    JOIN local_craftsmen lc on p.local_craftsmen_id = lc.id
    //    JOIN regions r on lc.regions_id = r.id
    //WHERE r.name = 'Arequipa'
    //GROUP BY r.name;
    @Query("SELECT NEW com.upc.hechoenperu.dtos.response.QuantityProductsByRegionResponseDTO (SUM(od.quantity), r.name) FROM OrderDetail od JOIN od.product p JOIN p.localCraftsman lc JOIN lc.region r GROUP BY r.name")
    List<QuantityProductsByRegionResponseDTO> quantityProductsByRegion();

    //SELECT SUM(quantity), c.name FROM order_details
    //JOIN products p on order_details.products_id = p.id
    //JOIN categories c on p.categories_id = c.id
    //GROUP BY c.name;}
    @Query("SELECT NEW com.upc.hechoenperu.dtos.response.QuantityProductsByCategoryResponseDTO (SUM(od.quantity), c.name) FROM OrderDetail od JOIN od.product p JOIN p.category c GROUP BY c.name")
    List<QuantityProductsByCategoryResponseDTO> quantityProductsByCategory();
}
