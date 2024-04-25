package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.CommentDTO;
import com.upc.hechoenperu.dtos.response.QuantityCommentsByRegionDTOResponse;
import com.upc.hechoenperu.entities.Comment;
import com.upc.hechoenperu.iservices.services.CommentService;
import com.upc.hechoenperu.util.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private DTOConverter dtoConverter;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/comment")
    public ResponseEntity<CommentDTO> insert(@RequestBody CommentDTO commentDTO){
        Comment comment = dtoConverter.convertToEntity(commentDTO, Comment.class);
        comment = commentService.insert(comment);
        commentService.updateAverageRatingProduct(comment.getId());
        commentDTO = dtoConverter.convertToDto(comment, CommentDTO.class);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDTO>> findByProductId(@RequestParam Long productId){
        List<Comment> comments = commentService.findByProductId(productId);
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> dtoConverter.convertToDto(comment, CommentDTO.class)).toList();
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/commentsQuantityByRegion")
    public ResponseEntity<List<QuantityCommentsByRegionDTOResponse>> quantityCommentsByRegion(){
        List<QuantityCommentsByRegionDTOResponse> quantityCommentsByRegionDTOResponses = commentService.quantityCommentsByRegion();
        return new ResponseEntity<>(quantityCommentsByRegionDTOResponses, HttpStatus.OK);
    }
}
