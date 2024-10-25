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
            JwtAuthenticationResponse response = authenticationService.signup(signUpRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest) {
            JwtAuthenticationResponse response = authenticationService.signin(signInRequest);
            return ResponseEntity.ok(response);
    }
}
