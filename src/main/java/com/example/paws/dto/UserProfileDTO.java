package com.example.paws.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {

    private String firstName;

    private String lastName;

    private String email;

    private List<OrderDTO> orders;

    private List<AdoptionRequestDTO> adoptionRequests;

    private List<UserProfileListingDTO> adoptionListings;

    private ImageDTO profileImage;
}
