package com.gamzebolat.service.impl;

import com.gamzebolat.Dto.OrderDto;
import com.gamzebolat.Dto.OrderItemDto;
import com.gamzebolat.entity.*;
import com.gamzebolat.mapper.OrderMapper;
import com.gamzebolat.repository.CustomerRepository;
import com.gamzebolat.repository.OrderRepository;
import com.gamzebolat.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderMapper orderMapper;

    private double calculateOrderItemTotalPrice(OrderItem orderItem) {
        return orderItem.getUnitPrice() * orderItem.getQuantity();
    }


    private double calculateOrderTotalPrice(Order order) {
        return order.getOrderItems().stream()
                .mapToDouble(this::calculateOrderItemTotalPrice)
                .sum();
    }

    @Override
    public OrderDto getOrderForCode(String orderCode) {
        Optional<Order> optionalOrder = orderRepository.findByOrderCode(orderCode);

        if(optionalOrder.isEmpty()){
            throw new RuntimeException("Order not found");
        }
        Order order = optionalOrder.get();

        OrderDto orderDto = orderMapper.toOrderDto(order);

            List<OrderItemDto> itemDtos = order.getOrderItems().stream()
                    .map(orderItem -> {
                        OrderItemDto dto = orderMapper.toOrderItemDto(orderItem);
                        dto.setTotalPrice(calculateOrderItemTotalPrice(orderItem));
                        return dto;
                    })
                    .toList();

            orderDto.setOrderItems(itemDtos);
            orderDto.setTotalPrice(calculateOrderTotalPrice(order));
        return orderDto;
    }

    @Override
    @Transactional
    public List<OrderDto> getAllOrdersForCustomer(int customerId) {
        Optional<Customer> optionalCustomer =customerRepository.findById(customerId);
        if(optionalCustomer.isEmpty()){
            throw new RuntimeException("Customer not found");
        }
        Customer customer = optionalCustomer.get();
        List<Order> orders = customer.getOrders();

        List<OrderDto> orderDtos = orders.stream()
                .map(order -> {
                    OrderDto orderDto = orderMapper.toOrderDto(order);
                    orderDto.setTotalPrice(calculateOrderTotalPrice(order));

                    List<OrderItemDto> items = order.getOrderItems().stream()
                            .map(oi -> {
                                OrderItemDto dto = orderMapper.toOrderItemDto(oi);
                                dto.setTotalPrice(calculateOrderItemTotalPrice(oi));
                                return dto;
                            })
                            .toList();

                    orderDto.setOrderItems(items);
                    return orderDto;
                })
                .toList();
        return orderDtos;
    }

    @Override
    public Order createOrderFromCart(Cart cart) {
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderDate(new Date());
        order.setOrderCode(UUID.randomUUID().toString());
        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setProductName(cartItem.getProduct().getProductName());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setUnitPrice(cartItem.getUnitPrice());
                    return orderItem;
                })
                .toList();

        order.setOrderItems(orderItems);
        return orderRepository.save(order);
    }

    public OrderDto convertToDto(Order order) {

        OrderDto orderDto = orderMapper.toOrderDto(order);
        orderDto.setTotalPrice(calculateOrderTotalPrice(order));

        List<OrderItemDto> items = new ArrayList<>();

        for (OrderItem oi : order.getOrderItems()) {
            OrderItemDto dto = orderMapper.toOrderItemDto(oi);
            dto.setTotalPrice(calculateOrderItemTotalPrice(oi));
            items.add(dto);
        }
        orderDto.setOrderItems(items);
        return orderDto;
    }
}
