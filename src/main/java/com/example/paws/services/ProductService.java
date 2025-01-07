package com.example.paws.services;

import com.example.paws.dao.ProductRepository;
import com.example.paws.dto.ProductDTO;
import com.example.paws.entities.*;
import com.example.paws.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final Mapper mapper;

    public Page<ProductDTO> getAllProducts(int pageNumber, int pageSize) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<ProductDTO> productDTOs = productPage.getContent().stream()
                .map(mapper::mapProductToProductDto)
                .toList();
        return new PageImpl<>(productDTOs, productPage.getPageable(), productPage.getTotalElements());
    }

    public ProductDTO getProductById(Long id) {
        return mapper.mapProductToProductDto(productRepository.findById(id).orElse(null));
    }

    public Product saveProduct(ProductDTO productDTO, List<MultipartFile> imageFiles) {
        if (imageFiles == null || imageFiles.isEmpty()) {
            throw new IllegalArgumentException("Image files cannot be null or empty.");
        }

        Product product = mapper.toProduct(productDTO);

        List<Image> images = product.getImages();
        for (MultipartFile file : imageFiles) {
            Image image = new Image();
            try {
                image.setData(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to convert image file", e);
            }
            image.setProduct(product);
            images.add(image);
        }

        product.setImages(images);

        return productRepository.save(product);
    }


    //    public Page<ProductDTO> filterProducts(int pageNumber, int pageSize,Long categoryId, String petType, Double minPrice, Double maxPrice) {
//        Page<Product> productPage = productRepository.filterProducts(PageRequest.of(pageNumber, pageSize),categoryId, petType, minPrice, maxPrice);
//        List<ProductDTO> productDTOs = productPage.getContent().stream()
//                .map(mapper::mapProductToProductDto)
//                .toList();
//        return new PageImpl<>(productDTOs, productPage.getPageable(), productPage.getTotalElements());
//    }
    public Page<ProductDTO> filterProducts(
            int size,
            int page,
            Long categoryId,
            List<String> petTypes,
            Double minPrice,
            Double maxPrice
    ) {
        Specification<Product> spec = Specification.where(ProductSpecification.hasCategoryId(categoryId))
                .and(ProductSpecification.hasPetTypes(petTypes))
                .and(ProductSpecification.hasPriceBetween(minPrice, maxPrice));

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(spec, pageable);

        return productPage.map(mapper::mapProductToProductDto);
    }

}
