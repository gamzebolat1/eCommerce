package com.gamzebolat.service;

import com.gamzebolat.Dto.DtoOrder;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.Order;

import java.util.List;

public interface IOrderService {
    public DtoOrder getOrderForCode(String orderCode);
    public List<DtoOrder> getAllOrdersForCustomer(int customerId);
    public Order createOrderFromCart(Cart cart);
    public DtoOrder convertToDto(Order order);
}
