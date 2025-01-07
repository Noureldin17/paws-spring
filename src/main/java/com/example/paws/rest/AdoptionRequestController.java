package com.example.paws.rest;

import com.example.paws.dto.AdoptionRequestDTO;
import com.example.paws.dto.ApiResponse;
import com.example.paws.entities.AdoptionRequest;
import com.example.paws.services.AdoptionRequestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/adoption-request")
public class AdoptionRequestController {

    private final AdoptionRequestService adoptionRequestService;

    @PostMapping("/create/{listingId}")
    public ResponseEntity<ApiResponse<Object>> createRequest(@PathVariable Long listingId, HttpServletRequest request) {
        String userEmail = (String) request.getAttribute("userEmail");
        AdoptionRequestDTO createdRequest = adoptionRequestService.createRequest(listingId, userEmail);
        ApiResponse<Object> response = ApiResponse.builder()
                .status("SUCCESS")
                .message("A Request for adoption listing with ID " + listingId + " has been sent to the owner!")
                .response(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/approve")
    public ResponseEntity<ApiResponse<String>> approveRequest(@RequestParam long requestId,HttpServletRequest request){
        String userEmail = (String) request.getAttribute("userEmail");
        adoptionRequestService.approveRequest(requestId,userEmail);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status("SUCCESS")
                .message("Request Approved!")
                .response("SUCCESS")
                .build();
        return ResponseEntity.ok(response);
    }
    @PostMapping("/reject")
    public ResponseEntity<ApiResponse<String>> rejectRequest(@RequestParam long requestId,HttpServletRequest request){
        String userEmail = (String) request.getAttribute("userEmail");
        adoptionRequestService.rejectRequest(requestId,userEmail);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status("SUCCESS")
                .message("Request Rejected!")
                .response("SUCCESS")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listing/{listingId}")
    public ResponseEntity<ApiResponse<List<AdoptionRequestDTO>>> getRequestsForListing(@PathVariable Long listingId) {
        List<AdoptionRequestDTO> requests = adoptionRequestService.getRequestsByListingId(listingId);
        ApiResponse<List<AdoptionRequestDTO>> response = ApiResponse.<List<AdoptionRequestDTO>>builder()
                .status("SUCCESS")
                .message("")
                .response(requests)
                .build();
        return ResponseEntity.ok(response);
    }
}
