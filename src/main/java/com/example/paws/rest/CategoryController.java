package com.example.paws.rest;

import com.example.paws.dto.ApiResponse;
import com.example.paws.entities.Category;
import com.example.paws.exception.InvalidCredentialsException;
import com.example.paws.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> addCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.addCategory(category);
        ApiResponse<Category> response = ApiResponse.<Category>builder()
                .status("SUCCESS")
                .message("")
                .response(createdCategory)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        ApiResponse<List<Category>> response = ApiResponse.<List<Category>>builder()
                .status("SUCCESS")
                .message("")
                .response(categories)
                .build();
        return ResponseEntity.ok(response);
    }
}
