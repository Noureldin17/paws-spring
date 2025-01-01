package com.example.paws.dto;

import com.example.paws.entities.AdoptionRequest;

import java.time.LocalDateTime;

public class UserProfileRequestsDTO {
    private Long requestId;

    private UserDTO user;

    private LocalDateTime requestDate;

    private AdoptionRequest.RequestStatus status;

    private AdoptionListingDTO adoptionListing;
}
