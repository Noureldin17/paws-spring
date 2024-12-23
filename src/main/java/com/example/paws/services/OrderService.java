package com.example.paws.services;
import com.example.paws.dao.OrderRepository;
import com.example.paws.dao.ProductRepository;
import com.example.paws.dao.UserRepository;
import com.example.paws.dto.OrderDTO;
import com.example.paws.dto.OrderRequest;
import com.example.paws.entities.Order;
import com.example.paws.entities.OrderItem;
import com.example.paws.entities.Product;
import com.example.paws.entities.User;
import com.example.paws.exception.InvalidRequestException;
import com.example.paws.exception.ResourceNotFoundException;
import com.example.paws.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final Mapper mapper;
    public List<OrderDTO> getAllOrders(String userEmail) {
        return mapper.mapOrdersListToOrdersDtoList(orderRepository.findOrdersByUser_Email(userEmail));
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public OrderDTO saveOrder(List<OrderRequest> orderItems, String userEmail) {
        if (orderItems.isEmpty()) {
            throw new InvalidRequestException("Order must contain at least one item.");
        }
        Optional<User> user = userRepository.findByEmail(userEmail);
        if (user.isEmpty()){
            throw new InvalidRequestException("An Error occurred during processing user request");
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setUser(user.get());
        List<OrderItem> orderItemEntities = new ArrayList<>();

        for (OrderRequest orderRequest : orderItems) {
            Product product = productRepository.findById(orderRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product with ID " + orderRequest.getProductId() + " not found."
                    ));

            if (product.getStockQuantity() < orderRequest.getQuantity()) {
                throw new InvalidRequestException(
                        "Product with ID " + orderRequest.getProductId() + " does not have enough stock."
                );
            }

            product.setStockQuantity(product.getStockQuantity() - orderRequest.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(orderRequest.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItemEntities.add(orderItem);
        }

        order.setOrderItems(orderItemEntities);

        return mapper.mapOrdersToOrdersDto(orderRepository.save(order));
    }

}
