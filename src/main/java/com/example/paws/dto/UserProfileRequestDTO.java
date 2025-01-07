package com.example.paws.dto;

import com.example.paws.entities.AdoptionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequestDTO {
    private Long requestId;

    private UserDTO user;

    private LocalDateTime requestDate;

    private AdoptionRequest.RequestStatus status;
}
