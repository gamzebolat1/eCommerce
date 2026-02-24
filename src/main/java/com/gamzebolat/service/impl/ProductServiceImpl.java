package com.gamzebolat.service.impl;

import com.gamzebolat.Dto.ProductDto;
import com.gamzebolat.entity.*;
import com.gamzebolat.mapper.ProductMapper;
import com.gamzebolat.repository.ProductRepository;
import com.gamzebolat.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto createProduct(Product product) {
        Product newProduct=new Product();
        newProduct.setProductName(product.getProductName());
        newProduct.setStock(product.getStock());
        newProduct.setPrice(product.getPrice());
        Product savedProduct = productRepository.save(newProduct);

        ProductDto productDto =productMapper.toProductDto(savedProduct);
        return productDto;
    }

    @Override
    public ProductDto getProduct(int Id) {
        Product product = productRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return productMapper.toProductDto(product);
    }

    @Override
    public ProductDto updateProduct(int Id , Product newProduct) {
        Product product = productRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (newProduct.getProductName() != null) {
            product.setProductName(newProduct.getProductName());
        }

        if (newProduct.getPrice() != null) {
            product.setPrice(newProduct.getPrice());
        }

        if (newProduct.getStock() != null) {
            product.setStock(newProduct.getStock());
        }
        Product updatedProduct = productRepository.save(product);
        return productMapper.toProductDto(updatedProduct);

    }

    @Override
    public void deleteProduct(int Id) {
        Optional<Product> optional=productRepository.findById(Id);
        if(optional.isPresent()){
            productRepository.delete(optional.get());
        }
    }

    @Override
    public void checkStock(Cart cart) {
        for (CartItem item : cart.getCartItems()) {
            Product product = productRepository.findById(
                    item.getProduct().getId()
            ).orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException(
                        product.getProductName() + " için yeterli stok yok"
                );
            }
        }
    }

    @Override
    public void decreaseStock(List<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toProductDtoList(products);
    }


}
