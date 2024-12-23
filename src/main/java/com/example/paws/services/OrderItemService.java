package com.example.paws.services;

import com.example.paws.dao.OrderItemRepository;
import com.example.paws.dto.OrderItemDTO;
import com.example.paws.entities.OrderItem;
import com.example.paws.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final Mapper mapper;

    public List<OrderItemDTO> getAllOrderItems() {
        return mapper.mapToOrderItemsDto(orderItemRepository.findAll());
    }

    public OrderItemDTO getOrderItemById(Long id) {
        return mapper.mapToOrderItemDto(orderItemRepository.findById(id).orElse(null));
    }

    public OrderItemDTO saveOrderItem(OrderItem orderItem) {
        return mapper.mapToOrderItemDto(orderItemRepository.save(orderItem));
    }
}
