package com.gamzebolat.service.impl;

import com.gamzebolat.Dto.DtoOrder;
import com.gamzebolat.Dto.DtoOrderItem;
import com.gamzebolat.entity.*;
import com.gamzebolat.repository.CartRepository;
import com.gamzebolat.repository.CustomerRepository;
import com.gamzebolat.repository.OrderRepository;
import com.gamzebolat.repository.ProductRepository;
import com.gamzebolat.service.IOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }




    @Override
    public DtoOrder getOrderForCode(String orderCode) {
        Optional<Order> optionalOrder = orderRepository.findByOrderCode(orderCode);

        if(optionalOrder.isEmpty()){
            throw new RuntimeException("Order not found");
        }
        Order order = optionalOrder.get();
        DtoOrder dtoOrder = new DtoOrder();
        BeanUtils.copyProperties(order,dtoOrder);

        if(order.getOrderItems() != null){
            if (dtoOrder.getOrderItems() == null) {
                dtoOrder.setOrderItems(new ArrayList<>());
            }
            for (OrderItem orderItem : order.getOrderItems()) {
                DtoOrderItem dtoOrderItem=new DtoOrderItem();
                dtoOrderItem.setProductName(orderItem.getProductName());
                dtoOrderItem.setUnitPrice(orderItem.getUnitPrice());
                dtoOrderItem.setTotalPrice(orderItem.getUnitPrice() * orderItem.getQuantity());

                dtoOrder.getOrderItems().add(dtoOrderItem);
            }
        }

        return dtoOrder;
    }

    @Override
    @Transactional
    public List<DtoOrder> getAllOrdersForCustomer(int customerId) {
        Optional<Customer> optionalCustomer =customerRepository.findById(customerId);
        if(optionalCustomer.isEmpty()){
            throw new RuntimeException("Customer not found");
        }
        Customer customer = optionalCustomer.get();
        List<Order> orders = customer.getOrders();
        List<DtoOrder> dtoOrders = new ArrayList<>();
        for (Order order : orders) {
            DtoOrder dtoOrder = new DtoOrder();

            dtoOrder.setTotalPrice(order.getTotalPrice());

            List<DtoOrderItem> items = new ArrayList<>();
            for (OrderItem oi : order.getOrderItems()) {
                DtoOrderItem dto = new DtoOrderItem();
                dto.setProductName(oi.getProductName());
                dto.setUnitPrice(oi.getUnitPrice());
                dto.setTotalPrice(oi.getUnitPrice() * oi.getQuantity());
                items.add(dto);
            }
            dtoOrder.setOrderItems(items);
            dtoOrders.add(dtoOrder);
        }

        return dtoOrders;
    }

}
