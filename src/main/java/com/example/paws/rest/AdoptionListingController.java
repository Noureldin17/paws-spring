package com.example.paws.rest;

import com.example.paws.entities.AdoptionListing;
import com.example.paws.services.AdoptionListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/adoption")
public class AdoptionListingController {

    private final AdoptionListingService adoptionListingService;

    @PostMapping("/create")
    public ResponseEntity<AdoptionListing> createAdoptionListing(@RequestBody AdoptionListing listing) {
        AdoptionListing createdListing = adoptionListingService.createAdoptionListing(listing);
        return ResponseEntity.ok(createdListing);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AdoptionListing>> getAllListings() {
        List<AdoptionListing> listings = adoptionListingService.getAllListings();
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdoptionListing> getListingById(@PathVariable Long id) {
        AdoptionListing listing = adoptionListingService.getListingById(id);
        return ResponseEntity.ok(listing);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
        adoptionListingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }
}
