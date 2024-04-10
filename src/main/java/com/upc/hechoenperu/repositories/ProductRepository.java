package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
