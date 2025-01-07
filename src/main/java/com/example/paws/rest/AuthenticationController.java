package com.example.paws.rest;

import com.example.paws.dto.*;
import com.example.paws.services.AuthenticationService;
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
    public ResponseEntity<ApiResponse<JwtAuthenticationResponse>> signup(@RequestBody SignUpRequest signUpRequest) {
        JwtAuthenticationResponse authResponse = authenticationService.signup(signUpRequest);
        ApiResponse<JwtAuthenticationResponse> response = ApiResponse.<JwtAuthenticationResponse>builder()
                .status("SUCCESS")
                .message("")
                .response(authResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<JwtAuthenticationResponse>> signin(@RequestBody SignInRequest signInRequest) {
        JwtAuthenticationResponse authResponse = authenticationService.signin(signInRequest);
        ApiResponse<JwtAuthenticationResponse> response = ApiResponse.<JwtAuthenticationResponse>builder()
                .status("SUCCESS")
                .message("")
                .response(authResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
