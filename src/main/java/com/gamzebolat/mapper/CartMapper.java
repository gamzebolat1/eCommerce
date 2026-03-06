package com.gamzebolat.mapper;

import com.gamzebolat.Dto.CartDto;
import com.gamzebolat.Dto.CartItemDto;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.CartItem;
import com.gamzebolat.entity.Customer;
import com.gamzebolat.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    //Entityde product nesnesi var ama Dto'da yok sadece productName var
    @Mapping(source = "product.productName", target = "productName")
    CartItemDto toCartItemDto(CartItem cartItem);

    List<CartItemDto> toCartItemDtoList(List<CartItem> cartItems);
    CartDto toCartDto(Cart cart);

    //otomatik generate işlemi yapılamıyor
    default Cart fromCustomer(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setTotalPrice(0.0);
        cart.setCartItems(new ArrayList<>());
        return cart;
    }

    default CartItem toCartItem(Cart cart, Product product, int quantity) {
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setUnitPrice(product.getPrice());
        return item;
    }

}
