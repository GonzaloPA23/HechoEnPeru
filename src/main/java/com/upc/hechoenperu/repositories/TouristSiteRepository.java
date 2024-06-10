package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.TouristSite;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TouristSiteRepository extends JpaRepository<TouristSite, Long> {
    @Query("SELECT ts FROM TouristSite ts")
    List<TouristSite> listTouristSitesByPage(Pageable pageable);
    @Query("SELECT t FROM TouristSite t JOIN t.region r WHERE r.id = :id")
    List<TouristSite> listTouristSitesByRegion(Long id);
    @Query("SELECT ts FROM TouristSite ts WHERE ts.region.id = :regionId")
    List<TouristSite> listTouristSitesByRegionId(Long regionId, Pageable pageable);
}
