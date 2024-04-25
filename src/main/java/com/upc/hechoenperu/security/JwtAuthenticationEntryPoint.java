package com.upc.hechoenperu.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // Configura la respuesta para enviar un JSON con el mensaje de error
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Crea un mapa para almacenar el mensaje de error
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", "Unauthorized"); // Aquí puedes personalizar el mensaje de error

        // Convierte el mapa en JSON y lo envía en el cuerpo de la respuesta
        ObjectMapper mapper = new ObjectMapper();
        OutputStream out = response.getOutputStream();
        mapper.writeValue(out, errorResponse);
        out.flush();
    }
}
