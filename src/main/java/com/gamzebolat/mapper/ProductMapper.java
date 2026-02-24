package com.gamzebolat.mapper;

import com.gamzebolat.Dto.ProductDto;
import com.gamzebolat.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toProductDto(Product product);
    List<ProductDto> toProductDtoList(List<Product> products);
}
