package com.gamzebolat.controller;

import com.gamzebolat.Dto.OrderDto;
import com.gamzebolat.facade.OrderFacade;
import com.gamzebolat.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final IOrderService orderService;
    private final OrderFacade orderFacade;


    @PostMapping("/placeOrder/{cartId}")
    public OrderDto placeOrder(@PathVariable(name = "cartId") int cartId) {
        return orderFacade.placeOrder(cartId);
    }

    @GetMapping("/getOrderForCode/{orderCode}")
    public OrderDto getOrderForCode(@PathVariable(name = "orderCode") String orderCode) {
        return orderService.getOrderForCode(orderCode);
    }

    @GetMapping("/getAllOrdersForCustomer/{customerId}")
    public List<OrderDto> getAllOrdersForCustomer(@PathVariable(name = "customerId") int customerId) {
        return orderService.getAllOrdersForCustomer(customerId);
    }
}
