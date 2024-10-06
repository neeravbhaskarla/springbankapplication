package com.practise.demo.controller;

import com.practise.demo.constants.AccountConstants;
import com.practise.demo.dto.CustomerDto;
import com.practise.demo.dto.ResponseDto;
import com.practise.demo.model.Accounts;
import com.practise.demo.model.Customer;
import com.practise.demo.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountsController {

    IAccountsService accountsService;
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto){
        accountsService.createAccount(customerDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(AccountConstants.MESSAGE_201, AccountConstants.STATUS_201));
    }

    @GetMapping("/customers")
    public ResponseEntity<Iterable<Customer>> getAllCustomers(){
        Iterable<Customer> customers = accountsService.getAllCustomers();
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .body(customers);
    }

    @GetMapping("/accounts")
    public ResponseEntity<Iterable<Accounts>> getAllAccounts(){
        Iterable<Accounts> accounts = accountsService.getAllAccounts();
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .body(accounts);
    }

    @GetMapping("/accounts/{customerName}")
    public ResponseEntity<Accounts> getAccountByCustomerName(@PathVariable String customerName){
        Accounts account = accountsService.getAccountDetailsByCustomerName(customerName);
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .body(account);
    }
}
