package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.Region;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    // Ordenar por nombre en forma ascendente
    List<Region> findAllByOrderByNameAsc();
    List<Region> findAllByNameContaining(String name);
    // SELECT * FROM regions OFFSET :offset LIMIT :limit
    @Query("SELECT r FROM Region r ORDER BY r.name ASC")
    List<Region> listRegionsByPageModeUser(Pageable pageable);
    //SELECT id,name,history FROM regions OFFSET :offset LIMIT :limit
    @Query("SELECT r FROM Region r ORDER BY r.id ASC")
    List<Region> listRegionsByPageModeAdmin(Pageable pageable);
    @Query("SELECT r FROM Region r ORDER BY r.id ASC")
    List<Region> listAllRegions();
}
