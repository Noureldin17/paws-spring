package com.example.paws.rest;

import com.example.paws.dto.ApiResponse;
import com.example.paws.dto.ProductDTO;
import com.example.paws.entities.PetType;
import com.example.paws.services.PetTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pet-types")
public class PetTypeController {

    private final PetTypeService petTypeService;

    @PostMapping
    public ResponseEntity<ApiResponse<PetType>> createPetType(@RequestBody PetType petType) {
        PetType savedPetType = petTypeService.addPetType(petType.getName());
        ApiResponse<PetType> response = ApiResponse.<PetType>builder()
                .status("SUCCESS")
                .message("")
                .response(savedPetType)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PetType>> updatePetType(@PathVariable Long id, @RequestParam String name) {
        PetType petType = petTypeService.updatePetType(id, name);
        ApiResponse<PetType> response = ApiResponse.<PetType>builder()
                .status("SUCCESS")
                .message("")
                .response(petType)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetType(@PathVariable Long id) {
        petTypeService.deletePetType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PetType>>> getAllPetTypes() {
        List<PetType> petTypes = petTypeService.getAllPetTypes();
        ApiResponse<List<PetType>> response = ApiResponse.<List<PetType>>builder()
                .status("SUCCESS")
                .message("")
                .response(petTypes)
                .build();
        return ResponseEntity.ok(response);
    }
}
