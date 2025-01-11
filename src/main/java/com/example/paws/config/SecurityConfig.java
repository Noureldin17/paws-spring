package com.example.paws.config;

import com.example.paws.filters.JwtAuthenticationFilter;
import com.example.paws.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/api/v1/signup","/api/v1/signin").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/pet-types").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/pet-types").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/pet-types").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/categories").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/categories").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/categories").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/products").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/products/filter").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/products").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/products").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/adoption").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/adoption").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/adoption").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/v1/adoption-request").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/v1/adoption-request").authenticated()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
