package com.upc.hechoenperu.controllers;
import com.upc.hechoenperu.dtos.UserDTO;
import com.upc.hechoenperu.dtos.request.UpdateProfileRequestDTO;
import com.upc.hechoenperu.entities.User;
import com.upc.hechoenperu.iservices.IUserService;
import com.upc.hechoenperu.util.DTOConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "User")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private DTOConverter dtoConverter;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "List all users")
    @GetMapping("/users")
    public ResponseEntity<?> list() {
        try {
            List<User> users = userService.list();
            List<UserDTO> userDTOs = users.stream().map(user -> dtoConverter.convertToDto(user, UserDTO.class)).toList();
            return new ResponseEntity<>(userDTOs, HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Update user profile")
    @PutMapping("/userUpdate/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateProfileRequestDTO updateProfileRequest,
                                          @PathVariable Long id) throws Exception {
        try{
            User user = dtoConverter.convertToEntity(updateProfileRequest, User.class);
            user.setId(id);
            user = userService.update(user);
            UserDTO userDTO = dtoConverter.convertToDto(user, UserDTO.class);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Delete account")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
        try {
            userService.delete(id);
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Search user by id")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> searchId(@PathVariable Long id) {
        UserDTO userDTO;
        try {
            User user = userService.findUserById(id);
            userDTO = dtoConverter.convertToDto(user, UserDTO.class);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
