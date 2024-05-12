package com.upc.hechoenperu.controllers;

import com.upc.hechoenperu.dtos.UserDTO;
import com.upc.hechoenperu.dtos.request.LoginUserRequestDTO;
import com.upc.hechoenperu.dtos.request.RegisterUserRequestDTO;
import com.upc.hechoenperu.dtos.response.JwtResponse;
import com.upc.hechoenperu.entities.User;
import com.upc.hechoenperu.iservices.IUserService;
import com.upc.hechoenperu.util.DTOConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@SecurityRequirements
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private IUserService userService;
    @Autowired
    private DTOConverter dtoConverter;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserRequestDTO request) {
        try {
            User user = dtoConverter.convertToEntity(request, User.class);
            user = userService.insert(user);
            UserDTO userDTO = dtoConverter.convertToDto(user, UserDTO.class);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserRequestDTO request) {
        try{
            JwtResponse jwtResponse = userService.login(request.getEmail(), request.getPassword());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", jwtResponse.getJwttoken());
            return ResponseEntity.ok().headers(responseHeaders).body(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
