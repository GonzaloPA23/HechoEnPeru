package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.dtos.response.RegionsByOffsetLimitResponseDTO;
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
    Region findByName(String name);
    // SELECT * FROM regions OFFSET :offset LIMIT :limit
    @Query("SELECT r FROM Region r")
    List<Region> listRegionsByPageModeUser(Pageable pageable);
    //SELECT id,name,history FROM regions OFFSET :offset LIMIT :limit
    @Query("SELECT NEW com.upc.hechoenperu.dtos.response.RegionsByOffsetLimitResponseDTO (r.id, r.name, r.history) FROM Region r")
    List<RegionsByOffsetLimitResponseDTO> listRegionsByPageModeAdmin(Pageable pageable);
}
