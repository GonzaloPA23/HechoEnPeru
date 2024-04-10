package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
