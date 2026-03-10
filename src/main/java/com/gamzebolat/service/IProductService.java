package com.gamzebolat.service;

import com.gamzebolat.Dto.ProductDto;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.OrderItem;
import com.gamzebolat.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    public ProductDto createProduct(Product product);
    public ProductDto getProduct(int Id);
    public ProductDto updateProduct(int Id , Product newProduct);
    public void deleteProduct(int Id);
    public void checkStock(Cart cart);
    public void decreaseStock(List<OrderItem> orderItems);
    public Page<ProductDto> getAllProducts(int page, int size);
}
