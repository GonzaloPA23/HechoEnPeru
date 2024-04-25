package com.upc.hechoenperu.controllers;
import com.upc.hechoenperu.dtos.UserDTO;
import com.upc.hechoenperu.entities.User;
import com.upc.hechoenperu.iservices.IUserService;
import com.upc.hechoenperu.util.DTOConverter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private DTOConverter dtoConverter;

    // SOLO LOS USUARIOS CON ROL ADMIN PUEDEN ACCEDER A ESTOS ENDPOINTS
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> list() {
        List<User> users = userService.list();
        List<UserDTO> userDTOs = users.stream().map(user -> dtoConverter.convertToDto(user, UserDTO.class)).toList();
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/user")
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO) throws Exception {
        User user = dtoConverter.convertToEntity(userDTO, User.class);
        user = userService.update(user);
        userDTO = dtoConverter.convertToDto(user, UserDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        userService.delete(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> searchId(@PathVariable Long id) {
        UserDTO userDTO;
        try {
            User user = userService.searchId(id);
            userDTO = dtoConverter.convertToDto(user, UserDTO.class);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            // Capturar la excepción de ResponseStatusException y devolverla directamente
            throw ex;
        } catch (Exception ex) {
            // Capturar cualquier otra excepción y devolver una respuesta personalizada
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
