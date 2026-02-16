package com.gamzebolat.service;

import com.gamzebolat.Dto.DtoCustomer;
import com.gamzebolat.Dto.DtoProduct;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.Customer;
import com.gamzebolat.entity.OrderItem;
import com.gamzebolat.entity.Product;

import java.util.List;

public interface IProductService {
    public DtoProduct createProduct(Product product);
    public DtoProduct getProduct(int Id);
    public DtoProduct updateProduct(int Id , Product newProduct);
    public void deleteProduct(int Id);
    public void checkStock(Cart cart);
    public void decreaseStock(List<OrderItem> orderItems);
}
