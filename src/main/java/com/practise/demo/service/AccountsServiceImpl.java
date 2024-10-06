package com.practise.demo.service;

import com.practise.demo.constants.AccountConstants;
import com.practise.demo.dto.CustomerDto;
import com.practise.demo.exceptions.CustomerAlreadyExistsException;
import com.practise.demo.exceptions.CustomerIdNotFoundException;
import com.practise.demo.exceptions.CustomerNameNotExistsException;
import com.practise.demo.mappers.CustomerMapper;
import com.practise.demo.model.Accounts;
import com.practise.demo.model.Customer;
import com.practise.demo.repository.AccountsRepository;
import com.practise.demo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService{
    AccountsRepository accountsRepository;
    CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number: "+customer.getMobileNumber());
        }

        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy(customer.getName());

        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccounts(savedCustomer));
    }

    @Override
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Iterable<Accounts> getAllAccounts() {
        return accountsRepository.findAll();
    }

    @Override
    public Accounts getAccountDetailsByCustomerName(String name) {
        Optional<Customer> customer = customerRepository.findByName(name);
        if(customer.isEmpty()){
            throw new CustomerNameNotExistsException("Customer name "+name+" don't exists");
        }
        Optional<Accounts> customerAccount = accountsRepository.findByCustomerId(customer.get().getCustomerId());

        if(customerAccount.isPresent()){
            return customerAccount.get();
        }
        else{
            throw new CustomerIdNotFoundException("Customer ID for the given customer name "+name+"is not found");
        }
    }

    public Accounts createNewAccounts(Customer customer) {
        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        Long randomAccountNumber = 10000000000L + new Random().nextInt(90000000);
        accounts.setAccountNumber(randomAccountNumber);
        accounts.setAccountType(AccountConstants.SAVINGS);
        accounts.setBranchAddress(AccountConstants.ADDRESS);
        accounts.setCreatedAt(LocalDateTime.now());
        accounts.setCreatedBy(customer.getName());
        return accounts;
    }
}
