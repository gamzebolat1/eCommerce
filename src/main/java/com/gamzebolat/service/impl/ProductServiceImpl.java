package com.gamzebolat.service.impl;

import com.gamzebolat.Dto.DtoCustomer;
import com.gamzebolat.Dto.DtoProduct;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.Customer;
import com.gamzebolat.entity.Product;
import com.gamzebolat.repository.CartRepository;
import com.gamzebolat.repository.CustomerRepository;
import com.gamzebolat.repository.ProductRepository;
import com.gamzebolat.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;


    public ProductServiceImpl(ProductRepository productRepository, CustomerRepository customerRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;

    }


    @Override
    public DtoProduct createProduct(Product product) {
        Product newProduct=new Product();
        newProduct.setProductName(product.getProductName());
        newProduct.setStock(product.getStock());
        newProduct.setPrice(product.getPrice());
        Product savedProduct = productRepository.save(newProduct);

        DtoProduct dtoProduct=new DtoProduct();
        BeanUtils.copyProperties(savedProduct,dtoProduct);
        return dtoProduct;

    }

    @Override
    public DtoProduct getProduct(int Id) {
    Optional<Product> optional=productRepository.findById(Id);
    DtoProduct dtoProduct=new DtoProduct();
    BeanUtils.copyProperties(optional.get(),dtoProduct);
        return dtoProduct;
    }

    @Override
    public DtoProduct updateProduct(int Id , Product newProduct) {
        Optional<Product> optional=productRepository.findById(Id);

        if(optional.isPresent()){
            Product product=optional.get();
            if(newProduct.getProductName()!=null){
                product.setProductName(newProduct.getProductName());
            }

            if(newProduct.getPrice()!=null) {
                product.setPrice(newProduct.getPrice());
            }
            if(newProduct.getStock()!=null){
                product.setStock(newProduct.getStock());
            }
            Product updatedProduct = productRepository.save(product);
            DtoProduct dtoProduct=new DtoProduct();
            BeanUtils.copyProperties(updatedProduct,dtoProduct);
            return  dtoProduct;
        }
        return null;
    }

    @Override
    public void deleteProduct(int Id) {
        Optional<Product> optional=productRepository.findById(Id);
        if(optional.isPresent()){
            productRepository.delete(optional.get());
        }
    }



}
