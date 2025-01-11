package com.example.paws.dto;

import com.example.paws.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

    private ImageDTO profileImage;
}
