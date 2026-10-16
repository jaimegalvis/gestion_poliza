package com.bolivar.gestion_poliza.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "x-api-key";
    private static final String VALID_API_KEY = "123456"; // El token exacto que pide la prueba

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Permitir el acceso a la consola de base de datos H2 sin pedir token
        if (path.startsWith("/h2-console")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtener el header de la petición
        String reqApiKey = request.getHeader(API_KEY_HEADER);

        // Validar si el token existe y es correcto
        if (reqApiKey == null || !reqApiKey.equals(VALID_API_KEY)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Acceso denegado: x-api-key invalido o ausente.\"}");
            return;
        }

        // Si el token es correcto, la petición continúa hacia el Controller
        filterChain.doFilter(request, response);
    }
}