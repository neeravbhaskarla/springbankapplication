package com.practise.demo.controller;

import com.practise.demo.constants.AccountConstants;
import com.practise.demo.dto.AccountsDto;
import com.practise.demo.dto.CustomerDto;
import com.practise.demo.dto.ResponseDto;
import com.practise.demo.service.IAccountsService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<Iterable<CustomerDto>> getAllCustomers(){
        Iterable<CustomerDto> customers = accountsService.getAllCustomers();
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .body(customers);
    }

    @GetMapping("/customers/{mobileNumber}")
    public ResponseEntity<CustomerDto> getCustomerByPhoneNumber(@PathVariable String mobileNumber){
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .body(accountsService.getCustomerByPhoneNumber(mobileNumber));
    }

    @GetMapping("/accounts")
    public ResponseEntity<Iterable<AccountsDto>> getAllAccounts(){
        Iterable<AccountsDto> accounts = accountsService.getAllAccounts();
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .body(accounts);
    }

    @GetMapping("/accounts/{customerName}")
    public ResponseEntity<AccountsDto> getAccountByCustomerName(@PathVariable String customerName){
        AccountsDto account = accountsService.getAccountDetailsByCustomerName(customerName);
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .body(account);
    }
}
