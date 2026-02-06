package com.gamzebolat.service;

import com.gamzebolat.Dto.DtoCustomer;
import com.gamzebolat.Dto.DtoProduct;
import com.gamzebolat.entity.Customer;
import com.gamzebolat.entity.Product;

public interface IProductService {
    public DtoProduct createProduct(Product product);
    public DtoProduct getProduct(int Id);
    public DtoProduct updateProduct(int Id , Product newProduct);
    public void deleteProduct(int Id);
    public DtoCustomer AddCustomer(Customer customer);
}
