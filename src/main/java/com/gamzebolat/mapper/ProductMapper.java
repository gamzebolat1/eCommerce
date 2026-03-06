package com.gamzebolat.mapper;

import com.gamzebolat.Dto.ProductDto;
import com.gamzebolat.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toProductDto(Product product);
    List<ProductDto> toProductDtoList(List<Product> products);

    default Product fromDto(Product product) {
        Product newProduct = new Product();
        newProduct.setProductName(product.getProductName());
        newProduct.setStock(product.getStock());
        newProduct.setPrice(product.getPrice());
        newProduct.setProductCode("PRD-" + java.util.UUID.randomUUID().toString().substring(0,6).toUpperCase());
        newProduct.setActive(true);
        return newProduct;
    }

}
