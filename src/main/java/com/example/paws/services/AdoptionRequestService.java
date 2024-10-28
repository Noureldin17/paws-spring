package com.example.paws.services;
import com.example.paws.entities.AdoptionRequest;
import org.springframework.stereotype.Service;
import com.example.paws.dao.AdoptionRequestRepository;
import java.util.List;

@Service
public class AdoptionRequestService {

    private final AdoptionRequestRepository adoptionRequestRepository;

    public AdoptionRequestService(AdoptionRequestRepository adoptionRequestRepository) {
        this.adoptionRequestRepository = adoptionRequestRepository;
    }

    public AdoptionRequest createRequest(AdoptionRequest request) {
        return adoptionRequestRepository.save(request);
    }

    public List<AdoptionRequest> getRequestsByListingId(Long listingId) {
        return adoptionRequestRepository.findByAdoptionListing_ListingId(listingId);
    }
}
