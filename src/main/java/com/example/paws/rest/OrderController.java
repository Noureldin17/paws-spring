package com.example.paws.rest;

import com.example.paws.dto.AdoptionRequestDTO;
import com.example.paws.dto.ApiResponse;
import com.example.paws.dto.OrderDTO;
import com.example.paws.dto.OrderRequest;
import com.example.paws.entities.Order;
import com.example.paws.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrders(HttpServletRequest request) {
        String userEmail = (String) request.getAttribute("userEmail");
        List<OrderDTO> orders = orderService.getAllOrders(userEmail);
        ApiResponse<List<OrderDTO>> response = ApiResponse.<List<OrderDTO>>builder()
                .status("SUCCESS")
                .message("")
                .response(orders)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createOrder(@RequestBody List<OrderRequest> orderItems, HttpServletRequest request) {
        String userEmail = (String) request.getAttribute("userEmail");
        orderService.saveOrder(orderItems, userEmail);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status("SUCCESS")
                .message("Order Status - PENDING")
                .response("Order placed successfully!")
                .build();
        return ResponseEntity.ok(response);
    }

}
