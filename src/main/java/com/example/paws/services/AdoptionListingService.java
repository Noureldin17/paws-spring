package com.example.paws.services;

import com.example.paws.dao.AdoptionListingRepository;
import com.example.paws.dto.AdoptionListingDTO;
import com.example.paws.dto.ProductDTO;
import com.example.paws.entities.*;
import com.example.paws.mappers.Mapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptionListingService {

    private final AdoptionListingRepository adoptionListingRepository;

    private final UserService userService;

    private final Mapper mapper;
    public AdoptionListingDTO createAdoptionListing(AdoptionListingDTO listing, List<MultipartFile> imageFiles, String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        if (imageFiles == null || imageFiles.isEmpty()) {
            throw new IllegalArgumentException("Image files cannot be null or empty.");
        }
        AdoptionListing adoptionListing = mapper.toAdoptionListing(listing);
        adoptionListing.setUser(user);
        List<Image> images = adoptionListing.getImages();
        for (MultipartFile file : imageFiles) {
            Image image = new Image();
            try {
                image.setData(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to convert image file", e);
            }
            image.setAdoptionListing(adoptionListing);
            images.add(image);
        }
        adoptionListing.setImages(images);

        return mapper.mapListingToListingDto(adoptionListingRepository.save(adoptionListing));
    }

//    public Page<AdoptionListingDTO> getAllListings(int pageNumber, int pageSize, String petTypeName, Integer minAge, Integer maxAge) {
//        Page<AdoptionListing> listings = adoptionListingRepository.filterAdoptionListings(PageRequest.of(pageNumber, pageSize), petTypeName, minAge, maxAge);
//        List<AdoptionListingDTO> adoptionListingDTOS = listings.getContent().stream()
//                .map(mapper::mapListingToListingDto)
//                .toList();
//        return new PageImpl<>(adoptionListingDTOS, listings.getPageable(), listings.getTotalElements());
//    }
    public Page<AdoptionListingDTO> getAllListings(
            int pageNumber,
            int pageSize,
            List<String> petTypeNames,
            Integer minAge,
            Integer maxAge) {

        Specification<AdoptionListing> spec = Specification
                .where(AdoptionListingSpecification.isAvailable())
                .and(AdoptionListingSpecification.hasPetTypes(petTypeNames))
                .and(AdoptionListingSpecification.hasMinAge(minAge))
                .and(AdoptionListingSpecification.hasMaxAge(maxAge));

        Page<AdoptionListing> listings = adoptionListingRepository.findAll(spec, PageRequest.of(pageNumber, pageSize));

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
