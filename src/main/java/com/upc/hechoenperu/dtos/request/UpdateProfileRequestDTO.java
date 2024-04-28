package com.upc.hechoenperu.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequestDTO {
    @NotEmpty(message = "El nombre es requerido")
    @Size(min = 2, message = "El nombre debe tener por lo menos 2 caracteres y máximo 50 caracteres")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotEmpty(message = "El apellido es requerido")
    @Size(min = 2, message = "El apellido debe tener por lo menos 2 caracteres y máximo 50 caracteres")
    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastName;

    @NotEmpty(message = "La contraseña es requerida")
    @Size(min = 2, message = "La contraseña debe tener por lo menos 7 caracteres")
    private String password;
}
