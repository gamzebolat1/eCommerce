package com.gamzebolat.service;

import com.gamzebolat.Dto.CartDto;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.Customer;

public interface ICartService {
public CartDto getCart(int Id);
public CartDto AddProductToCart(int cartId, int productId);
public void removeProductFromCart(int cartId, int productId);
public void emptyCart(int cartId);
public Cart createCartForCustomer(Customer customer);
    public Cart getValidatedCart(int cartId);
    public void clearCart(Cart cart);
    public CartDto increaseQuantity(int cartId, int productId);
    public CartDto decreaseQuantity(int cartId, int productId);
}
