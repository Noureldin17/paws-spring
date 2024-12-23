package com.example.paws.services;
import com.example.paws.dao.AdoptionListingRepository;
import com.example.paws.dao.UserRepository;
import com.example.paws.dto.AdoptionRequestDTO;
import com.example.paws.entities.AdoptionListing;
import com.example.paws.entities.AdoptionRequest;
import com.example.paws.entities.User;
import com.example.paws.exception.InvalidRequestException;
import com.example.paws.exception.RequestAlreadyExistsException;
import com.example.paws.exception.ResourceNotFoundException;
import com.example.paws.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.paws.dao.AdoptionRequestRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdoptionRequestService {

    private final AdoptionRequestRepository adoptionRequestRepository;
    
    private final AdoptionListingRepository adoptionListingRepository;
    
    private final UserService userService;

    private final Mapper mapper;
    public AdoptionRequestDTO createRequest(Long listingId, String userEmail) {
        User user = userService.getUserByEmail(userEmail);

        Optional<AdoptionListing> listingOpt = adoptionListingRepository.findById( listingId);
        if (listingOpt.isEmpty()) {
            throw new ResourceNotFoundException("Adoption listing with ID " + listingId + " not found.");
        }

        AdoptionListing listing = listingOpt.get();
        if (!"AVAILABLE".equalsIgnoreCase(listing.getStatus().name())) {
            throw new InvalidRequestException("Adoption listing with ID " + listingId + " is no longer available.");
        }

        if(adoptionRequestRepository.existsAdoptionRequestByAdoptionListing_ListingIdAndUser_UserId(listingId,user.getUserId())){
            throw new RequestAlreadyExistsException("A Request already exists for this user for the listing with ID " + listingId);
        }

        AdoptionRequest request = mapper.toAdoptionRequest(listingId, user.getUserId());
        AdoptionRequest savedRequest = adoptionRequestRepository.save(request);
        return mapper.mapRequestToRequestDto(savedRequest);
    }


    public List<AdoptionRequestDTO> getRequestsByListingId(Long listingId) {
        return mapper.mapRequestsToRequestsDto(adoptionRequestRepository.findByAdoptionListing_ListingId(listingId));
    }
}
