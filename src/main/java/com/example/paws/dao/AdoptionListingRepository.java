package com.example.paws.dao;

import com.example.paws.entities.AdoptionListing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptionListingRepository extends JpaRepository<AdoptionListing, Long> {
    List<AdoptionListing> findByUser_UserId(Long userId);
}