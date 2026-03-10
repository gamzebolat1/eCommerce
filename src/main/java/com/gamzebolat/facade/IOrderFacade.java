package com.gamzebolat.facade;

import com.gamzebolat.Dto.OrderDto;

public interface IOrderFacade {
    public OrderDto placeOrder(int cartId);
}
