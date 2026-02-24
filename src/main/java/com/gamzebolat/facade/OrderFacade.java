package com.gamzebolat.facade;

import com.gamzebolat.Dto.OrderDto;
import com.gamzebolat.entity.*;
import com.gamzebolat.service.impl.CartServiceImpl;
import com.gamzebolat.service.impl.OrderServiceImpl;
import com.gamzebolat.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderFacade {

    private final CartServiceImpl cartService;
    private final OrderServiceImpl orderService;
    private final ProductServiceImpl productService;


    public OrderDto placeOrder(int cartId) {

        Cart cart = cartService.getValidatedCart(cartId);

        productService.checkStock(cart);

        Order order = orderService.createOrderFromCart(cart);

        productService.decreaseStock(order.getOrderItems());

        cartService.clearCart(cart);

        return orderService.convertToDto(order);
    }
}
