package com.example.paws.services;

import com.example.paws.dao.AdoptionListingRepository;
import com.example.paws.entities.AdoptionListing;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptionListingService {

    private final AdoptionListingRepository adoptionListingRepository;

    public AdoptionListingService(AdoptionListingRepository adoptionListingRepository) {
        this.adoptionListingRepository = adoptionListingRepository;
    }

    public AdoptionListing createAdoptionListing(AdoptionListing listing) {
        return adoptionListingRepository.save(listing);
    }

    public List<AdoptionListing> getAllListings() {
        return adoptionListingRepository.findAll();
    }

    public AdoptionListing getListingById(Long id) {
        return adoptionListingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));
    }

    public void deleteListing(Long id) {
        adoptionListingRepository.deleteById(id);
    }
}
