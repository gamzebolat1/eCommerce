package com.gamzebolat.facade;

import com.gamzebolat.Dto.DtoCustomer;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.Customer;
import com.gamzebolat.repository.CartRepository;
import com.gamzebolat.repository.CustomerRepository;
import com.gamzebolat.service.ICartService;
import com.gamzebolat.service.ICustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class CustomerFacade {

    private final  ICustomerService customerService;
    private final ICartService cartService;

    public CustomerFacade(  ICustomerService customerService, ICartService cartService) {
        this.customerService = customerService;
        this.cartService = cartService;
    }

    @Transactional
    public DtoCustomer addCustomer(Customer customer) {

        Customer savedCustomer=customerService.CreateCustomer(customer);
        cartService.createCartForCustomer(savedCustomer);
        DtoCustomer dtoCustomer = new DtoCustomer();
        BeanUtils.copyProperties(savedCustomer, dtoCustomer);

        return dtoCustomer;

    }
}
