package com.microservice.accounts.service;

import com.microservice.accounts.Dto.AccountDto;
import com.microservice.accounts.Dto.CustomerDto;

public interface IAccountsService {

    void createAccount(CustomerDto customerDto);

    CustomerDto fetchCustomer(String mobile);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteCustomer(String mobile);


}
