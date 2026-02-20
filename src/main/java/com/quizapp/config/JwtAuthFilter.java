package com.quizapp.config;

import com.quizapp.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    public JwtAuthFilter(JwtUtil jwtUtil, UsuarioService usuarioService) {
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Paso 1: Leer la cabecera Authorization de la petición
        String cabecera = request.getHeader("Authorization");

        // Paso 2: Si no hay cabecera o no empieza por "Bearer ", dejamos pasar sin hacer nada
        if (cabecera == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!cabecera.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Paso 3: Extraer el token (quitar los primeros 7 caracteres: "Bearer ")
        String token = cabecera.substring(7);

        // Paso 4: Comprobar si el token es válido
        boolean tokenValido = jwtUtil.esTokenValido(token);

        if (!tokenValido) {
            filterChain.doFilter(request, response);
            return;
        }

        // Paso 5: Obtener el nombre de usuario del token
        String username = jwtUtil.obtenerUsername(token);

        // Paso 6: Comprobar que el usuario existe y que no está ya autenticado
        boolean yaAutenticado = SecurityContextHolder.getContext().getAuthentication() != null;

        if (username == null || yaAutenticado) {
            filterChain.doFilter(request, response);
            return;
        }

        // Paso 7: Cargar los datos del usuario desde la base de datos
        UserDetails userDetails = usuarioService.loadUserByUsername(username);

        // Paso 8: Crear el objeto de autenticación con el usuario y sus permisos
        UsernamePasswordAuthenticationToken autenticacion = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        // Paso 9: Añadir los detalles de la petición (IP, etc.)
        WebAuthenticationDetailsSource detallesSource = new WebAuthenticationDetailsSource();
        autenticacion.setDetails(detallesSource.buildDetails(request));

        // Paso 10: Guardar la autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(autenticacion);

        // Paso 11: Dejar continuar la petición
        filterChain.doFilter(request, response);
    }
}