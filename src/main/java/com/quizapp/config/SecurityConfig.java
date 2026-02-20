package com.quizapp.config;

import com.quizapp.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioService usuarioService;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(UsuarioService usuarioService, JwtAuthFilter jwtAuthFilter) {
        this.usuarioService = usuarioService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .userDetailsService(usuarioService)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/login", "/registro",
                    "/css/**", "/js/**", "/images/**",
                    "/error/**", "/swagger-ui/**",
                    "/swagger-ui.html", "/api-docs/**",
                    "/generar-password", "/api/auth/**"
                ).permitAll()
                .requestMatchers(
                    "/categorias/nueva", "/categorias/editar/**",
                    "/categorias/eliminar/**", "/categorias/guardar",
                    "/preguntas/vf/nueva", "/preguntas/vf/editar/**",
                    "/preguntas/vf/eliminar/**", "/preguntas/vf/guardar",
                    "/preguntas/seleccion-unica/nueva", "/preguntas/seleccion-unica/editar/**",
                    "/preguntas/seleccion-unica/eliminar/**", "/preguntas/seleccion-unica/guardar",
                    "/preguntas/seleccion-multiple/nueva", "/preguntas/seleccion-multiple/editar/**",
                    "/preguntas/seleccion-multiple/eliminar/**", "/preguntas/seleccion-multiple/guardar",
                    "/preguntas/importar/**", "/api-externa/**"
                ).hasRole("ADMIN")
                .requestMatchers("/noticias/**").authenticated()
                .requestMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login-process")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/error/403")
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}