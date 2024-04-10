package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
}
