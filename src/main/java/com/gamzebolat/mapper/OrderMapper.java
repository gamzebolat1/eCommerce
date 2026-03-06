package com.gamzebolat.mapper;

import com.gamzebolat.Dto.OrderDto;
import com.gamzebolat.Dto.OrderItemDto;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.CartItem;
import com.gamzebolat.entity.Order;
import com.gamzebolat.entity.OrderItem;
import org.mapstruct.Mapper;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toOrderDto(Order order);

    OrderItemDto toOrderItemDto(OrderItem orderItem);

    List<OrderItemDto> toOrderItemDtoList(List<OrderItem> orderItems);


    default Order fromCart(Cart cart) {
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderDate(new Date());
        order.setOrderCode(UUID.randomUUID().toString());

        List<OrderItem> items = cart.getCartItems().stream()
                .map(cartItem -> toOrderItem(cartItem, order))
                .toList();

        order.setOrderItems(items);
        return order;
    }

    default OrderItem toOrderItem(CartItem cartItem, Order order) {
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(cartItem.getProduct());
        item.setProductName(cartItem.getProduct().getProductName());
        item.setQuantity(cartItem.getQuantity());
        item.setUnitPrice(cartItem.getUnitPrice());
        return item;
    }

}
