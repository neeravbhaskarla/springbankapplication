package com.practise.demo.service;

import com.practise.demo.dto.AccountsDto;
import com.practise.demo.dto.CustomerDto;
import com.practise.demo.model.Accounts;
import com.practise.demo.model.Customer;

public interface IAccountsService {
    void createAccount(CustomerDto customerDto);

    Iterable<Customer> getAllCustomers();

    Iterable<Accounts> getAllAccounts();

    Accounts getAccountDetailsByCustomerName(String name);

}
