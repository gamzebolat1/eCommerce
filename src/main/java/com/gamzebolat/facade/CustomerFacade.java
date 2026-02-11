package com.gamzebolat.facade;

import com.gamzebolat.Dto.DtoCustomer;
import com.gamzebolat.entity.Cart;
import com.gamzebolat.entity.Customer;
import com.gamzebolat.repository.CartRepository;
import com.gamzebolat.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class CustomerFacade {

    private final CustomerRepository customerRepository;

    public CustomerFacade(CustomerRepository customerRepository, CartRepository cartRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public DtoCustomer AddCustomer(Customer customer) {

        Customer newCustomer = new Customer();
        newCustomer.setUsername(customer.getUsername());
        newCustomer.setOrders(new ArrayList<>());

        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.setCartItems(new ArrayList<>());

        cart.setCustomer(newCustomer);
        newCustomer.setCart(cart);

        Customer savedCustomer = customerRepository.save(newCustomer);

        DtoCustomer dtoCustomer = new DtoCustomer();
        BeanUtils.copyProperties(savedCustomer, dtoCustomer);

        return dtoCustomer;
    }
}
