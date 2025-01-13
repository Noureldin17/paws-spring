package com.example.paws.dao;

import com.example.paws.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    void deleteAllByProduct_ProductId(Long productId);
    void deleteAllByAdoptionListing_ListingId(Long listingId);
    void deleteAllByUser_UserId(Long userId);
}
