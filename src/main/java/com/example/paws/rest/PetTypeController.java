package com.example.paws.rest;

import com.example.paws.entities.PetType;
import com.example.paws.services.PetTypeService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<PetType> createPetType(@RequestBody PetType petType) {
        PetType savedPetType = petTypeService.addPetType(petType.getName());
        return new ResponseEntity<>(savedPetType, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetType> updatePetType(@PathVariable Long id, @RequestParam String name) {
        PetType petType = petTypeService.updatePetType(id, name);
        return ResponseEntity.ok(petType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetType(@PathVariable Long id) {
        petTypeService.deletePetType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PetType>> getAllPetTypes() {
        return ResponseEntity.ok(petTypeService.getAllPetTypes());
    }
}
