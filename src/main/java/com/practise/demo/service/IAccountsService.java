package com.practise.demo.service;

import com.practise.demo.dto.AccountsDto;
import com.practise.demo.dto.CustomerDto;

public interface IAccountsService {
    void createAccount(CustomerDto customerDto);

    Iterable<CustomerDto> getAllCustomers();

    CustomerDto getCustomerByPhoneNumber(String phoneNumber);

    Iterable<AccountsDto> getAllAccounts();

    AccountsDto getAccountDetailsByCustomerName(String name);

    boolean updateCustomerandAccount(CustomerDto customerDto);

    boolean deleteCustomerByMobileNumber(String mobileNumber);

}
