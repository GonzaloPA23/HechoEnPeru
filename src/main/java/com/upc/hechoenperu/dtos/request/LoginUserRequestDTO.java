package com.upc.hechoenperu.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequestDTO {
    @NotBlank(message = "El email es requerido")
    private String email;

    @NotBlank(message = "La contraseña es requerida")
    private String password;
}
