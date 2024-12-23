package com.example.paws.services;

import com.example.paws.dao.AdoptionListingRepository;
import com.example.paws.dto.AdoptionListingDTO;
import com.example.paws.dto.ProductDTO;
import com.example.paws.entities.AdoptionListing;
import com.example.paws.entities.Product;
import com.example.paws.mappers.Mapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptionListingService {

    private final AdoptionListingRepository adoptionListingRepository;

    private final Mapper mapper;
    public AdoptionListingDTO createAdoptionListing(AdoptionListing listing) {
        return mapper.mapListingToListingDto(adoptionListingRepository.save(listing));
    }

    public Page<AdoptionListingDTO> getAllListings(int pageNumber, int pageSize, String petTypeName, Integer minAge, Integer maxAge) {
        Page<AdoptionListing> listings = adoptionListingRepository.filterAdoptionListings(PageRequest.of(pageNumber, pageSize), petTypeName, minAge, maxAge);
        List<AdoptionListingDTO> adoptionListingDTOS = listings.getContent().stream()
                .map(mapper::mapListingToListingDto)
                .toList();
        return new PageImpl<>(adoptionListingDTOS, listings.getPageable(), listings.getTotalElements());
    }

    public AdoptionListingDTO getListingById(Long id) {
        return adoptionListingRepository.findById(id).map(mapper::mapListingToListingDto)
                .orElseThrow(() -> new RuntimeException("Listing not found"));
    }

    public void deleteListing(Long id) {
        adoptionListingRepository.deleteById(id);
    }
}
