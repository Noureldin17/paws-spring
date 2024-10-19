package com.example.paws.rest;

import com.example.paws.dto.JwtAuthenticationResponse;
import com.example.paws.dto.SignInRequest;
import com.example.paws.dto.SignUpRequest;
import com.example.paws.services.AuthenticationService;
import com.example.paws.exception.UserAlreadyExistsException;
import com.example.paws.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {
    final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest signUpRequest) {
        try {
            JwtAuthenticationResponse response = authenticationService.signup(signUpRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    JwtAuthenticationResponse.builder()
                            .message(ex.getMessage())
                            .token(null)  // Or a different handling mechanism
                            .build()
            );
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest) {
        try {
            JwtAuthenticationResponse response = authenticationService.signin(signInRequest);
            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    JwtAuthenticationResponse.builder()
                            .message(ex.getMessage())
                            .token(null)  // Or a different handling mechanism
                            .build()
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    JwtAuthenticationResponse.builder()
                            .message(ex.getMessage())
                            .token(null)  // Or a different handling mechanism
                            .build()
            );
        }
    }
}
