package com.example.paws.dto;

import com.example.paws.entities.AdoptionRequest;
import com.example.paws.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionRequestDTO {

    private Long requestId;

    private UserDTO user;

    private LocalDateTime requestDate;

    private AdoptionRequest.RequestStatus status;

    private AdoptionListingDTO adoptionListing;
}
