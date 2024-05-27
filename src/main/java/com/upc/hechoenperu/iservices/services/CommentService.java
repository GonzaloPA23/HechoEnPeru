package com.upc.hechoenperu.iservices.services;

import com.upc.hechoenperu.dtos.response.QuantityCommentsByRegionResponseDTO;
import com.upc.hechoenperu.entities.Comment;
import com.upc.hechoenperu.entities.Product;
import com.upc.hechoenperu.entities.User;
import com.upc.hechoenperu.iservices.ICommentService;
import com.upc.hechoenperu.repositories.CommentRepository;
import com.upc.hechoenperu.repositories.ProductRepository;
import com.upc.hechoenperu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CommentService implements ICommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Comment insert(Comment comment) {
        Product product = productRepository.findById(comment.getProduct().getId()).orElse(null);
        User user = userRepository.findById(comment.getUser().getId()).orElse(null);
        if (user == null || !user.getEnabled()) {
            throw new IllegalArgumentException("The user does not exist or is not active");
        }
        if (product == null || !product.getEnabled()) {
            throw new IllegalArgumentException("The product does not exist or is not active");
        }
        if (comment.getRating() < 1 || comment.getRating() > 5) {
            throw new IllegalArgumentException("The rating must be between 1 and 5");
        }
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findByProductId(Long productId){
        if (productRepository.findById(productId).isEmpty() || !productRepository
                .findById(productId).get().getEnabled()) {
            throw new IllegalArgumentException("The product does not exist or is not active");
        }
        return commentRepository.findByProductId(productId);
    }

    @Override
    public Comment searchId(Long id) throws Exception {
        return commentRepository.findById(id).orElseThrow(() -> new Exception("Comment not found"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAverageRatingProduct(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            Long productId = comment.getProduct().getId();
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null && product.getAverageRating() != null) {
                float averageRating = product.getAverageRating();
                Long countComments = commentRepository.countByProductId(productId);
                averageRating = (averageRating * (countComments - 1) + comment.getRating()) / countComments;
                product.setAverageRating(averageRating);
                productRepository.save(product);
            }
        }
    }

    @Override
    public List<QuantityCommentsByRegionResponseDTO> quantityCommentsByRegion() {
        return commentRepository.quantityCommentsByRegion();
    }

    @Override
    public List<Comment> listCommentsByPage(Long productId, int offset, int limit){
        if (productRepository.findById(productId).isEmpty() || !productRepository
                .findById(productId).get().getEnabled()) {
            throw new IllegalArgumentException("The product does not exist or is not active");
        }
        Pageable pageable = PageRequest.of(offset, limit);
        return commentRepository.listCommentsByPage(productId, pageable);
    }
}
