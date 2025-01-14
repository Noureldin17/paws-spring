package com.example.paws.services;

import com.example.paws.dao.UserRepository;
import com.example.paws.dto.JwtAuthenticationResponse;
import com.example.paws.dto.SignInRequest;
import com.example.paws.dto.SignUpRequest;
import com.example.paws.entities.Role;
import com.example.paws.entities.User;
import com.example.paws.exception.InvalidCredentialsException;
import com.example.paws.exception.UserAlreadyExistsException;
import com.example.paws.mappers.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Mapper mapper;

    public JwtAuthenticationResponse signup(SignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists.");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        user = userService.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().user(mapper.mapUserToUserDTO(user)).message("Registration Success!").token(jwt).build();
    }

    public JwtAuthenticationResponse signin(SignInRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().user(mapper.mapUserToUserDTO(user)).message("Authentication Success!").token(jwt).build();
    }
}
