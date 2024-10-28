package com.example.paws.services;

import com.example.paws.dao.PetTypeRepository;
import com.example.paws.entities.PetType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetTypeService {
    private final PetTypeRepository petTypeRepository;

    public PetTypeService(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    public PetType addPetType(String name) {
        PetType petType = new PetType();
        petType.setName(name);
        return petTypeRepository.save(petType);
    }

    public PetType updatePetType(Long id, String name) {
        PetType petType = petTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PetType not found"));
        petType.setName(name);
        return petTypeRepository.save(petType);
    }

    public void deletePetType(Long id) {
        petTypeRepository.deleteById(id);
    }

    public List<PetType> getAllPetTypes() {
        return petTypeRepository.findAll();
    }
}
