package com.upc.hechoenperu.iservices;

import com.upc.hechoenperu.dtos.response.JwtResponse;
import com.upc.hechoenperu.entities.User;

import java.util.List;

public interface IUserService {
    User insert(User user);
    List<User> list();
    User findByEmail(String email);
    Boolean existsByEmail(String email);
    User update(User user) throws Exception;
    User searchId(Long id) throws Exception;
    void delete(Long id) throws Exception;
    JwtResponse login(String email, String password);
    void authenticate(String username, String password) throws Exception;
    User findUserById(Long id);
}
