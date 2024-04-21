package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByNameRole(Role.NameRole name); // Busca un rol por su nombre
    //Boolean existsByNameRole(String name); // Verifica si existe un rol por su nombre

}
