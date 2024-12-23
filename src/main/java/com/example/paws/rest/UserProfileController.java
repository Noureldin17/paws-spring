package com.example.paws.rest;

import com.example.paws.dto.UserProfileDTO;
import com.example.paws.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/userprofile")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserProfileDTO> getUserProfile(HttpServletRequest request) {
        String userEmail = (String) request.getAttribute("userEmail");

        if (userEmail == null) {
            return ResponseEntity.badRequest().build();
        }
        UserProfileDTO userProfile = userService.getUserProfileInfo(userEmail);
        return ResponseEntity.ok(userProfile);
    }
}
