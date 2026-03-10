package com.gamzebolat.facade.impl;

import com.gamzebolat.Dto.OrderDto;
import com.gamzebolat.entity.*;
import com.gamzebolat.facade.IOrderFacade;
import com.gamzebolat.mapper.OrderMapper;
import com.gamzebolat.service.impl.CartServiceImpl;
import com.gamzebolat.service.impl.OrderServiceImpl;
import com.gamzebolat.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderFacadeImpl implements IOrderFacade {

    private final CartServiceImpl cartService;
    private final OrderServiceImpl orderService;
    private final ProductServiceImpl productService;
    private final OrderMapper orderMapper;

    public OrderDto placeOrder(int cartId) {

        Cart cart = cartService.getValidatedCart(cartId);

        productService.checkStock(cart);

        Order order = orderService.createOrderFromCart(cart);

        productService.decreaseStock(order.getOrderItems());

        cartService.clearCart(cart);

        return orderMapper.toOrderDto(order);
    }
}
