package com.practise.demo.service;

import com.practise.demo.constants.AccountConstants;
import com.practise.demo.dto.AccountsDto;
import com.practise.demo.dto.CustomerDto;
import com.practise.demo.exceptions.CustomerAlreadyExistsException;
import com.practise.demo.exceptions.ResourceNotFoundException;
import com.practise.demo.mappers.AccountsMapper;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService{
    AccountsRepository accountsRepository;
    CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
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
    public Iterable<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
            .map(CustomerMapper::mapToCustomerDto)
            .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerByPhoneNumber(String mobileNumber){
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile number", mobileNumber));

        AccountsDto account = accountsRepository.findByCustomerId(customer.getCustomerId())
            .map(AccountsMapper::mapToAccountsDto)
            .orElseThrow(() -> new ResourceNotFoundException("Accounts", "Customer ID", String.valueOf(customer.getCustomerId())));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer);
        customerDto.setAccount(account);
        return customerDto;
    }
    @Override
    public Iterable<AccountsDto> getAllAccounts() {
        return accountsRepository.findAll().stream()
            .map(AccountsMapper::mapToAccountsDto)
            .collect(Collectors.toList());
    }

    @Override
    public AccountsDto getAccountDetailsByCustomerName(String name) {
        Optional<Customer> customer = customerRepository.findByName(name);
        if(customer.isEmpty()){
            throw new ResourceNotFoundException("Customer", "Customer name", name);
        }

        Accounts customerAccount = accountsRepository
            .findByCustomerId(customer.get().getCustomerId())
            .orElseThrow(() -> new ResourceNotFoundException("Accounts", "CustomerId", String.valueOf(customer.get().getCustomerId())));

        return AccountsMapper.mapToAccountsDto(customerAccount);
    }

    @Override
    public boolean updateCustomerandAccount(CustomerDto customerDto){
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccount();
        if(accountsDto != null){
            Accounts accounts = accountsRepository
                .findById(accountsDto.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "Account Number", String.valueOf(accountsDto.getAccountNumber())));
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Customer ID", String.valueOf(customerId)));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteCustomerByMobileNumber(String mobileNumber){
        Optional<Customer> customer = customerRepository.findByMobileNumber(mobileNumber);
        if(customer.isEmpty()){
            return false;
        }
        Long finalCustomer = customer.get().getCustomerId();
        customerRepository.deleteById(finalCustomer);
        accountsRepository.deleteByCustomerId(finalCustomer);
        return true;
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
