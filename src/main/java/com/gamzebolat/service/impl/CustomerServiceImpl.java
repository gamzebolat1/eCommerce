package com.gamzebolat.service.impl;

import com.gamzebolat.entity.Customer;
import com.gamzebolat.repository.CustomerRepository;
import com.gamzebolat.service.ICustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public Customer CreateCustomer(Customer customer) {

            Customer newCustomer = new Customer();
            newCustomer.setUsername(customer.getUsername());
            newCustomer.setOrders(new ArrayList<>());
            return customerRepository.save(newCustomer);

    }
}
