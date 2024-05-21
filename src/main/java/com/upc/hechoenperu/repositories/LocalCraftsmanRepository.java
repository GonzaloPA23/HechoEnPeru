package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.dtos.response.LocalCraftsmenByOffsetLimitResponseDTO;
import com.upc.hechoenperu.entities.LocalCraftsman;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalCraftsmanRepository extends JpaRepository<LocalCraftsman, Long>{
    // SELECT * FROM local_craftsmen WHERE enabled = true OFFSET :offset LIMIT :limit"
    @Query("SELECT l FROM LocalCraftsman l WHERE l.enabled = true")
    List<LocalCraftsman> listLocalCraftsmenByPageModeUser(Pageable pageable);
    // SELECT local_craftsmen.id, full_name, specialty, experience, r.id, r.name, enabled  FROM local_craftsmen JOIN regions r on r.id = local_craftsmen.regions_id OFFSET :offset LIMIT :limit;
    @Query("SELECT NEW com.upc.hechoenperu.dtos.response.LocalCraftsmenByOffsetLimitResponseDTO (l.id, l.fullName, l.specialty, l.experience, r.id, r.name, l.enabled) FROM LocalCraftsman l JOIN l.region r")
    List<LocalCraftsmenByOffsetLimitResponseDTO> listLocalCraftsmenByPageModeAdmin(Pageable pageable);
}
