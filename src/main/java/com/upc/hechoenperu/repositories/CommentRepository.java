package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.dtos.response.QuantityCommentsByRegionDTOResponse;
import com.upc.hechoenperu.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Long countByProductId(Long productId);
    List<Comment> findByProductId(Long productId);
    @Query("SELECT NEW com.upc.hechoenperu.dtos.response.QuantityCommentsByRegionDTOResponse (c.product.localCraftsman.region.name, COUNT(c)) FROM Comment c GROUP BY c.product.localCraftsman.region.name")
    List<QuantityCommentsByRegionDTOResponse> quantityCommentsByRegion();

}
