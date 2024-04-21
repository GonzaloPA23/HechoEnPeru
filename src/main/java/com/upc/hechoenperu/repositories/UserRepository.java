package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email); // Busca un usuario por su email
    Boolean existsByEmail(String email); // Verifica si existe un usuario por su email
}
