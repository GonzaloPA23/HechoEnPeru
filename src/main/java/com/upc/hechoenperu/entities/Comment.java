package com.upc.hechoenperu.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "products_id", nullable = false)
    private Product product;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "text_commentary", nullable = false, length = Integer.MAX_VALUE)
    private String textCommentary;
}