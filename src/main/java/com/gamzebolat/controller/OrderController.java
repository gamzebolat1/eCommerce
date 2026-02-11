package com.gamzebolat.controller;

import com.gamzebolat.Dto.DtoOrder;
import com.gamzebolat.facade.OrderFacade;
import com.gamzebolat.service.IOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")

public class OrderController {
    private final IOrderService orderService;
    private final OrderFacade orderFacade;

    public OrderController(IOrderService orderService, OrderFacade orderFacade) {
        this.orderService = orderService;
        this.orderFacade = orderFacade;
    }

    @PostMapping("/placeOrder/{cartId}")
    public DtoOrder placeOrder(@PathVariable(name = "cartId") int cartId) {
        return orderFacade.placeOrder(cartId);
    }

    @GetMapping("/getOrderForCode/{orderCode}")
    public DtoOrder getOrderForCode(@PathVariable(name = "orderCode") String orderCode) {
        return orderService.getOrderForCode(orderCode);
    }

    @GetMapping("/getAllOrdersForCustomer/{customerId}")
    public List<DtoOrder> getAllOrdersForCustomer(@PathVariable(name = "customerId") int customerId) {
        return orderService.getAllOrdersForCustomer(customerId);
    }
}
