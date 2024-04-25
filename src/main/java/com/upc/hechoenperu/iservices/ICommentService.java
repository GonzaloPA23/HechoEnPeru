package com.upc.hechoenperu.iservices;

import com.upc.hechoenperu.dtos.response.QuantityCommentsByRegionDTOResponse;
import com.upc.hechoenperu.entities.Comment;

import java.util.List;


public interface ICommentService {
    Comment insert(Comment comment);
    Comment searchId(Long id) throws Exception;
    void updateAverageRatingProduct(Long id);
    List<Comment> findByProductId(Long productId);
    List<QuantityCommentsByRegionDTOResponse> quantityCommentsByRegion();


}
