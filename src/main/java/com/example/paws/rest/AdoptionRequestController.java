package com.example.paws.rest;

import com.example.paws.entities.AdoptionRequest;
import com.example.paws.services.AdoptionRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/adoption-request")
public class AdoptionRequestController {

    private final AdoptionRequestService adoptionRequestService;

    @PostMapping("/create")
    public ResponseEntity<AdoptionRequest> createRequest(@RequestBody AdoptionRequest request) {
        AdoptionRequest createdRequest = adoptionRequestService.createRequest(request);
        return ResponseEntity.ok(createdRequest);
    }

    @GetMapping("/listing/{listingId}")
    public ResponseEntity<List<AdoptionRequest>> getRequestsForListing(@PathVariable Long listingId) {
        List<AdoptionRequest> requests = adoptionRequestService.getRequestsByListingId(listingId);
        return ResponseEntity.ok(requests);
    }
}
