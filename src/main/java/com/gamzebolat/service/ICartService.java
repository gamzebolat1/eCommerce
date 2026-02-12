package com.gamzebolat.service;

import com.gamzebolat.Dto.DtoCart;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.Customer;
import com.gamzebolat.entity.Product;

public interface ICartService {
public DtoCart getCart(int Id);
public DtoCart AddProductToCart(int cartId,int productId);
public void removeProductFromCart(int cartId, int productId);
public void emptyCart(int cartId);
public Cart createCartForCustomer(Customer customer);
}
