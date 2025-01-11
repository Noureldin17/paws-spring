package com.example.paws.rest;

import com.example.paws.dto.ApiResponse;
import com.example.paws.dto.ProductDTO;
import com.example.paws.entities.Product;
import com.example.paws.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Page<ProductDTO> productPage = productService.getAllProducts(page,size);
        ApiResponse<Page<ProductDTO>> response = ApiResponse.<Page<ProductDTO>>builder()
                .status("SUCCESS")
                .message("")
                .response(productPage)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .status("SUCCESS")
                .message("")
                .response(product)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> filterProducts(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<String> petType,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        Page<ProductDTO> filteredProducts = productService.filterProducts(size, page, categoryId, petType, minPrice, maxPrice);
        ApiResponse<Page<ProductDTO>> response = ApiResponse.<Page<ProductDTO>>builder()
                .status("SUCCESS")
                .message("")
                .response(filteredProducts)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createProduct(
            @RequestPart ProductDTO product,
            @RequestPart List<MultipartFile> imageFiles) {
        Product savedProduct = productService.saveProduct(product, imageFiles);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status("SUCCESS")
                .message("A new product has been added with ID: " + savedProduct.getProductId() + " & Name: " + savedProduct.getName())
                .response("SUCCESS")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<String>> editProduct(
            @PathVariable Long productId,
            @RequestPart ProductDTO product,
            @RequestPart(required = false) List<MultipartFile> imageFiles) {
        Product updatedProduct = productService.updateProduct(productId, product, imageFiles);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status("SUCCESS")
                .message("Product with ID: " + updatedProduct.getProductId() + " has been updated successfully.")
                .response("SUCCESS")
                .build();
        return ResponseEntity.ok(response);
    }

}
