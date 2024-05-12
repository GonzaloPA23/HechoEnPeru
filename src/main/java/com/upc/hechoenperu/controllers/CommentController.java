package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.CommentDTO;
import com.upc.hechoenperu.dtos.response.QuantityCommentsByRegionDTOResponse;
import com.upc.hechoenperu.entities.Comment;
import com.upc.hechoenperu.iservices.ICommentService;
import com.upc.hechoenperu.util.DTOConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment")
@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @Autowired
    private DTOConverter dtoConverter;

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Insert a new comment")
    @PostMapping("/comment")
    public ResponseEntity<?> insert(@RequestBody CommentDTO commentDTO){
        try {
            Comment comment = dtoConverter.convertToEntity(commentDTO, Comment.class);
            comment = commentService.insert(comment);
            commentService.updateAverageRatingProduct(comment.getId());
            commentDTO = dtoConverter.convertToDto(comment, CommentDTO.class);
            return new ResponseEntity<>(commentDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "List all comments by product id")
    @GetMapping("/comments")
    public ResponseEntity<?> findByProductId(@RequestParam Long productId){
        try {
            List<Comment> comments = commentService.findByProductId(productId);
            List<CommentDTO> commentDTOS = comments.stream().map(comment -> dtoConverter.convertToDto(comment, CommentDTO.class)).toList();
            return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "List quantity of comments by region")
    @GetMapping("/commentsQuantityByRegion")
    public ResponseEntity<?> quantityCommentsByRegion(){
        try {
            List<QuantityCommentsByRegionDTOResponse> quantityCommentsByRegionDTOResponses = commentService.quantityCommentsByRegion();
            return new ResponseEntity<>(quantityCommentsByRegionDTOResponses, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
