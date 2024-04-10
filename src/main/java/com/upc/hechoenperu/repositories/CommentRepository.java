package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
