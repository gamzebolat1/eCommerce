package com.gamzebolat.facade;

import com.gamzebolat.Dto.DtoOrder;
import com.gamzebolat.Dto.DtoOrderItem;
import com.gamzebolat.entity.*;
import com.gamzebolat.repository.CartRepository;
import com.gamzebolat.repository.OrderRepository;
import com.gamzebolat.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderFacade {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public OrderFacade(OrderRepository orderRepository, CartRepository cartRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public DtoOrder placeOrder(int cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderDate(new Date());
        order.setOrderCode(UUID.randomUUID().toString());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = productRepository.findById(
                    cartItem.getProduct().getId()
            ).orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException(
                        product.getProductName() + " için yeterli stok yok"
                );
            }
        }

        for (CartItem cartItem : cart.getCartItems()) {

            Product product = productRepository.findById(
                    cartItem.getProduct().getId()
            ).orElseThrow();

            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setProductName(product.getProductName());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // sepeti temizle
        cart.getCartItems().clear();
        cart.setTotalPrice(0);
        cartRepository.save(cart);

        DtoOrder dtoOrder = new DtoOrder();
        dtoOrder.setTotalPrice(savedOrder.getTotalPrice());

        List<DtoOrderItem> dtoItems = new ArrayList<>();
        for (OrderItem oi : savedOrder.getOrderItems()) {
            DtoOrderItem dtoItem = new DtoOrderItem();
            dtoItem.setProductName(oi.getProductName());
            dtoItem.setUnitPrice(oi.getUnitPrice());
            dtoItem.setTotalPrice(oi.getUnitPrice() * oi.getQuantity());
            dtoItems.add(dtoItem);
        }

        dtoOrder.setOrderItems(dtoItems);
        return dtoOrder;
    }
}
