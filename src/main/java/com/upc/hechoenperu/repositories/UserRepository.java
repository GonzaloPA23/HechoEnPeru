package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);
    Boolean existsByEmail(String email);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.nameRole = 'USER' AND u.id = :id")
    User findUserById(Long id);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.nameRole = 'USER'")
    List<User> list();
}
