package com.example.paws.dto;

import com.example.paws.entities.Category;
import com.example.paws.entities.Image;
import com.example.paws.entities.PetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long productId;

    private String name;

    private String description;

    private Double price;

    private Category category;

    private PetType petType;

    private Integer stockQuantity;

    private List<ImageDTO> images;
}
