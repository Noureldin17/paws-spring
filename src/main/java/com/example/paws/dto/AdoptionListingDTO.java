package com.example.paws.dto;
import com.example.paws.entities.AdoptionListing;
import com.example.paws.entities.PetType;
import com.example.paws.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionListingDTO {

    private Long listingId;

    private UserDTO user;

    private PetType petType;

    private String breed;

    private Integer age;

    private String description;

    private LocalDateTime listedDate;

    private AdoptionListing.ListingStatus status;

    private List<ImageDTO> images;

    private List<AdoptionRequestDTO> adoptionRequests;
}
