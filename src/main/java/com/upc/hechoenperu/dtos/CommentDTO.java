package com.upc.hechoenperu.dtos;

import com.upc.hechoenperu.entities.Product;
import com.upc.hechoenperu.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private Integer rating;
    private String textCommentary;
    private Product product;
    private User user;
}
