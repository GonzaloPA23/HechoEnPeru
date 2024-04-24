package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    // Find by name
    Region findByName(String name);
}
