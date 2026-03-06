package com.gamzebolat.facade.impl;

import com.gamzebolat.Dto.CustomerDto;
import com.gamzebolat.entity.Customer;

public interface ICustomerFacade {
    public CustomerDto addCustomer(Customer customer);
}
