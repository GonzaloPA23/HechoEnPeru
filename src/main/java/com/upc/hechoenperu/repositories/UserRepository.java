package com.upc.hechoenperu.repositories;

import com.upc.hechoenperu.dtos.response.UserByOffsetLimitResponseDTO;
import com.upc.hechoenperu.entities.User;
import org.springframework.data.domain.Pageable;
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
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.nameRole = 'USER' ORDER BY u.id ASC")
    List<User> list();
    //@Query(value = "SELECT users.id, name, last_name, email, r.name_role, date_created,enabled FROM users JOIN users_roles ur on users.id = ur.user_id JOIN roles r on ur.role_id = r.id OFFSET :offset LIMIT :limit", nativeQuery = true)
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.nameRole = 'USER' order by u.id ASC")
    List<User> listUsersByPageModeAdmin(Pageable pageable);
}
