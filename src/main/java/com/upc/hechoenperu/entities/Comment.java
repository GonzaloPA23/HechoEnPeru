package com.upc.hechoenperu.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "products_id", nullable = false)
    private Product products;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "users_id", nullable = false)
    private User users;

    @Column(name = "quantity_likes")
    private Integer quantityLikes;

    @Column(name = "quantity_dislikes")
    private Integer quantityDislikes;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "text_commentary", nullable = false, length = Integer.MAX_VALUE)
    private String textCommentary;

}