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

    public void approveRequest(long requestId, String userEmail) {
        User user = userService.getUserByEmail(userEmail);

        Optional<AdoptionRequest> requestOpt = adoptionRequestRepository.findById(requestId);
        if (requestOpt.isEmpty()) {
            throw new ResourceNotFoundException("Adoption request with ID " + requestId + " not found.");
        }

        AdoptionRequest request = requestOpt.get();
        AdoptionListing listing = request.getAdoptionListing();

        // Check if the user owns the listing
        if (!listing.getUser().getUserId().equals(user.getUserId())) {
            throw new InvalidRequestException("You are not authorized to approve requests for this listing.");
        }

        if (!"PENDING".equalsIgnoreCase(request.getStatus().name())) {
            throw new InvalidRequestException("Adoption request with ID " + requestId + " cannot be approved as it is not in PENDING status.");
        }

        if (!"AVAILABLE".equalsIgnoreCase(listing.getStatus().name())) {
            throw new InvalidRequestException("Adoption listing with ID " + listing.getListingId() + " is no longer available.");
        }

        // Approve the current request
        request.setStatus(AdoptionRequest.RequestStatus.APPROVED);
        adoptionRequestRepository.save(request);

        // Reject other pending requests for the same listing
        List<AdoptionRequest> otherRequests = adoptionRequestRepository.findByAdoptionListing_ListingId(listing.getListingId());
        for (AdoptionRequest otherRequest : otherRequests) {
            if (otherRequest.getRequestId() != requestId && "PENDING".equalsIgnoreCase(otherRequest.getStatus().name())) {
                otherRequest.setStatus(AdoptionRequest.RequestStatus.REJECTED);
                adoptionRequestRepository.save(otherRequest);
            }
        }

        // Update the listing status to ADOPTED
        listing.setStatus(AdoptionListing.ListingStatus.ADOPTED);
        adoptionListingRepository.save(listing);
    }

    public void rejectRequest(long requestId, String userEmail) {
        User user = userService.getUserByEmail(userEmail);

        Optional<AdoptionRequest> requestOpt = adoptionRequestRepository.findById(requestId);
        if (requestOpt.isEmpty()) {
            throw new ResourceNotFoundException("Adoption request with ID " + requestId + " not found.");
        }

        AdoptionRequest request = requestOpt.get();
        AdoptionListing listing = request.getAdoptionListing();

        // Check if the user owns the listing
        if (!listing.getUser().getUserId().equals(user.getUserId())) {
            throw new InvalidRequestException("You are not authorized to reject requests for this listing.");
        }

        if (!"PENDING".equalsIgnoreCase(request.getStatus().name())) {
            throw new InvalidRequestException("Adoption request with ID " + requestId + " cannot be rejected as it is not in PENDING status.");
        }

        // Reject the current request
        request.setStatus(AdoptionRequest.RequestStatus.REJECTED);
        adoptionRequestRepository.save(request);
    }


    public List<AdoptionRequestDTO> getRequestsByListingId(Long listingId) {
        return mapper.mapRequestsToRequestsDto(adoptionRequestRepository.findByAdoptionListing_ListingId(listingId));
    }
}
