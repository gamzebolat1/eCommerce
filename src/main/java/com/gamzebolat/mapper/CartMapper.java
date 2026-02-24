package com.gamzebolat.mapper;

import com.gamzebolat.Dto.CartDto;
import com.gamzebolat.Dto.CartItemDto;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    //Entityde product nesnesi var ama Dto'da yok sadece productName var
    @Mapping(source = "product.productName", target = "productName")
    CartItemDto toCartItemDto(CartItem cartItem);

    List<CartItemDto> toCartItemDtoList(List<CartItem> cartItems);
    CartDto toCartDto(Cart cart);
}
