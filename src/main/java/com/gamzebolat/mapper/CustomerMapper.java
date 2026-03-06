package com.gamzebolat.mapper;

import com.gamzebolat.Dto.CustomerDto;
import com.gamzebolat.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toCustomerDto(Customer customer);
}
