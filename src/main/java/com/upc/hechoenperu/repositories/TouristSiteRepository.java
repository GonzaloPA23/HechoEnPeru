package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.TouristSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristSiteRepository extends JpaRepository<TouristSite, Long> {
}
