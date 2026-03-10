package com.gamzebolat.facade.impl;

import com.gamzebolat.Dto.CustomerDto;
import com.gamzebolat.entity.Customer;
import com.gamzebolat.facade.ICustomerFacade;
import com.gamzebolat.mapper.CustomerMapper;
import com.gamzebolat.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerFacadeImpl implements ICustomerFacade {

    private final  ICustomerService customerService;
    private final CustomerMapper customerMapper;



    @Transactional
    public CustomerDto addCustomer(Customer customer) {

        Customer savedCustomer = customerService.CreateCustomer(customer);
        return customerMapper.toCustomerDto(savedCustomer);
    }
}
