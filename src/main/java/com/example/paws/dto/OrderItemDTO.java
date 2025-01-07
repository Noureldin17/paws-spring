package com.example.paws.dto;

import com.example.paws.entities.Order;
import com.example.paws.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private ProductDTO product;

    private Integer quantity;

    private Double price;
}
