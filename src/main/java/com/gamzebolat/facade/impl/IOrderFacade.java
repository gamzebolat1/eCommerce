package com.gamzebolat.facade.impl;

import com.gamzebolat.Dto.OrderDto;

public interface IOrderFacade {
    public OrderDto placeOrder(int cartId);
}
