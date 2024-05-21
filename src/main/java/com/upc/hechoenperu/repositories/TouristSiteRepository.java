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
}
