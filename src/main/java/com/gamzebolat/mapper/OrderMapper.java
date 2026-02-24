package com.gamzebolat.mapper;

import com.gamzebolat.Dto.OrderDto;
import com.gamzebolat.Dto.OrderItemDto;
import com.gamzebolat.entity.Order;
import com.gamzebolat.entity.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toOrderDto(Order order);

    OrderItemDto toOrderItemDto(OrderItem orderItem);

    List<OrderItemDto> toOrderItemDtoList(List<OrderItem> orderItems);
}
