package com.example.paws.dao;

import com.example.paws.entities.AdoptionListing;
import com.example.paws.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdoptionListingRepository extends JpaRepository<AdoptionListing, Long> {
    List<AdoptionListing> findByUser_UserId(Long userId);

    @Query("SELECT l FROM AdoptionListing l "
            + "LEFT JOIN l.petType pt "
            + "WHERE l.status = 'AVAILABLE' "
            + "AND (:petTypeName IS NULL OR pt.name = :petTypeName) "
            + "AND (:minAge IS NULL OR l.age >= :minAge) "
            + "AND (:maxAge IS NULL OR l.age <= :maxAge)")
    Page<AdoptionListing> filterAdoptionListings(
            Pageable pageable,
            @Param("petTypeName") String petTypeName,
            @Param("minAge") Integer minAge,
            @Param("maxAge") Integer maxAge);
}