package com.gamzebolat.service;

import com.gamzebolat.Dto.DtoOrder;

import java.util.List;

public interface IOrderService {
    public DtoOrder placeOrder(int cartId);
    public DtoOrder getOrderForCode(String orderCode);
    public List<DtoOrder> getAllOrdersForCustomer(int customerId);
}
