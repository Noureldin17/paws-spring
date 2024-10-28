package com.example.paws.dao;

import com.example.paws.entities.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetTypeRepository extends JpaRepository<PetType, Long> {
    Optional<PetType> findByName(String name);
}
