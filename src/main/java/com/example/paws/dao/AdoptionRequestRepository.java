package com.example.paws.dao;
import com.example.paws.entities.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {
    List<AdoptionRequest> findByAdoptionListing_ListingId(Long listingId);
}
