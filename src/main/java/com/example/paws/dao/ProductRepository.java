package com.example.paws.dao;
import java.util.List;
import com.example.paws.entities.Product;
import com.example.paws.entities.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPetType(PetType petType);
}
