package com.gamzebolat.service;

import com.gamzebolat.Dto.OrderDto;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.Order;

import java.util.List;

public interface IOrderService {
    public OrderDto getOrderForCode(String orderCode);
    public List<OrderDto> getAllOrdersForCustomer(int customerId);
    public Order createOrderFromCart(Cart cart);
    public OrderDto convertToDto(Order order);
}
