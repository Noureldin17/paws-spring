package com.example.paws.rest;

import com.example.paws.dto.AdoptionListingDTO;
import com.example.paws.dto.ApiResponse;
import com.example.paws.entities.AdoptionListing;
import com.example.paws.services.AdoptionListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/adoption")
public class AdoptionListingController {

    private final AdoptionListingService adoptionListingService;

    @PostMapping("/create")
    public ResponseEntity<AdoptionListingDTO> createAdoptionListing(@RequestBody AdoptionListing listing) {
        AdoptionListingDTO createdListing = adoptionListingService.createAdoptionListing(listing);
        return ResponseEntity.ok(createdListing);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<AdoptionListingDTO>>> getAllListings(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) String petType,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge
    ) {
        Page<AdoptionListingDTO> listings = adoptionListingService.getAllListings(page, size, petType, minAge, maxAge);
        ApiResponse<Page<AdoptionListingDTO>> response = ApiResponse.<Page<AdoptionListingDTO>>builder()
                .status("SUCCESS")
                .message("")
                .response(listings)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdoptionListingDTO> getListingById(@PathVariable Long id) {
        AdoptionListingDTO listing = adoptionListingService.getListingById(id);
        return ResponseEntity.ok(listing);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        adoptionListingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }
}
