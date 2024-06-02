package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.dtos.response.QuantityCommentsByRegionResponseDTO;
import com.upc.hechoenperu.entities.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Long countByProductId(Long productId);
    List<Comment> findByProductId(Long productId);
    @Query("SELECT COUNT(c) FROM Comment c")
    Long getQuantityComments();
    @Query("SELECT NEW com.upc.hechoenperu.dtos.response.QuantityCommentsByRegionResponseDTO (c.product.localCraftsman.region.name, (COUNT(c) * 1.0 ) / :quantityComments * 100 ) FROM Comment c GROUP BY c.product.localCraftsman.region.name")
    List<QuantityCommentsByRegionResponseDTO> quantityCommentsByRegion(@Param("quantityComments") Long quantityComments);
    @Query("SELECT c FROM Comment c WHERE c.product.id = :productId ORDER BY c.id DESC")
    List<Comment> listCommentsByPage(Long productId, Pageable pageable);

}
