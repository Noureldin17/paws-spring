package com.example.paws.rest;

import com.example.paws.dto.AdoptionListingDTO;
import com.example.paws.dto.ApiResponse;
import com.example.paws.dto.ProductDTO;
import com.example.paws.entities.AdoptionListing;
import com.example.paws.services.AdoptionListingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/adoption")
public class AdoptionListingController {

    private final AdoptionListingService adoptionListingService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AdoptionListingDTO>> createAdoptionListing(@RequestPart AdoptionListingDTO adoptionListing,
                                                                              @RequestPart List<MultipartFile> imageFiles, HttpServletRequest request
    ) {
        String userEmail = (String) request.getAttribute("userEmail");
        AdoptionListingDTO createdListing = adoptionListingService.createAdoptionListing(adoptionListing, imageFiles, userEmail);
        ApiResponse<AdoptionListingDTO> response = ApiResponse.<AdoptionListingDTO>builder()
                .status("SUCCESS")
                .message("")
                .response(createdListing)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdoptionListingDTO>>> getAllListings(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) List<String> petType,
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
