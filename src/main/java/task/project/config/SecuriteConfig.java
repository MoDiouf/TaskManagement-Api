package task.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import task.project.config.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecuriteConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // ❌ Désactive CSRF (normal pour API REST en JWT)
            .csrf(csrf -> csrf.disable())

            // ❌ Pas de session HTTP → tout passe par le token
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 🔐 Règles d’accès
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login").permitAll()      // endpoint login public
                .requestMatchers("/api/auth/register").permitAll()  
                .requestMatchers("/ swagger-ui.html").permitAll()  // si tu as inscription
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()               // tout le reste protégé
            )

            // ➕ Ajout du filtre JWT AVANT le filtre Spring
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 🔑 Encodage sécurisé des mots de passe
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
