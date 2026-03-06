package com.gamzebolat.service.impl;

import com.gamzebolat.entity.Customer;
import com.gamzebolat.repository.CustomerRepository;
import com.gamzebolat.service.ICartService;
import com.gamzebolat.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final ICartService cartService;

    @Override
    public Customer CreateCustomer(Customer customer) {

            Customer newCustomer = new Customer();
            newCustomer.setUsername(customer.getUsername());
            newCustomer.setOrders(new ArrayList<>());

        Customer savedCustomer = customerRepository.save(newCustomer);
        cartService.createCartForCustomer(savedCustomer);
        return savedCustomer;
    }
}
