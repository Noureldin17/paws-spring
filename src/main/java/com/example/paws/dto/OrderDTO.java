package com.example.paws.dto;

import com.example.paws.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long orderId;

    private LocalDateTime orderDate;

    private Order.OrderStatus status;

    private Double totalAmount;

    private List<OrderItemDTO> orderItems;
}
