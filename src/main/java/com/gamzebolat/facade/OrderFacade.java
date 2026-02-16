package com.gamzebolat.facade;

import com.gamzebolat.Dto.DtoOrder;
import com.gamzebolat.Dto.DtoOrderItem;
import com.gamzebolat.entity.*;
import com.gamzebolat.repository.CartRepository;
import com.gamzebolat.repository.OrderRepository;
import com.gamzebolat.repository.ProductRepository;
import com.gamzebolat.service.impl.CartServiceImpl;
import com.gamzebolat.service.impl.OrderServiceImpl;
import com.gamzebolat.service.impl.ProductServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderFacade {

    private final CartServiceImpl cartService;
    private final OrderServiceImpl orderService;
    private final ProductServiceImpl productService;

    public OrderFacade(
            CartServiceImpl cartService,
            OrderServiceImpl orderService,
            ProductServiceImpl productService
    ) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.productService = productService;
    }

    public DtoOrder placeOrder(int cartId) {

        Cart cart = cartService.getValidatedCart(cartId);

        productService.checkStock(cart);

        Order order = orderService.createOrderFromCart(cart);

        productService.decreaseStock(order.getOrderItems());

        cartService.clearCart(cart);

        return orderService.convertToDto(order);
    }
}
