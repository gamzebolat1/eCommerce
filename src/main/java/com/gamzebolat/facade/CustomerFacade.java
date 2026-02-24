package com.gamzebolat.facade;

import com.gamzebolat.Dto.CustomerDto;
import com.gamzebolat.entity.Customer;
import com.gamzebolat.service.ICartService;
import com.gamzebolat.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerFacade {

    private final  ICustomerService customerService;
    private final ICartService cartService;



    @Transactional
    public CustomerDto addCustomer(Customer customer) {

        Customer savedCustomer=customerService.CreateCustomer(customer);
        cartService.createCartForCustomer(savedCustomer);
        CustomerDto dtoCustomer = new CustomerDto();
        BeanUtils.copyProperties(savedCustomer, dtoCustomer);

        return dtoCustomer;

    }
}
