package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.LocalCraftsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalCraftsmanRepository extends JpaRepository<LocalCraftsman, Long>{
}
