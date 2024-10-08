package com.practise.demo.controller;

import com.practise.demo.constants.AccountConstants;
import com.practise.demo.dto.AccountsDto;
import com.practise.demo.dto.CustomerDto;
import com.practise.demo.dto.ResponseDto;
import com.practise.demo.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Tag(
    name = "CRUD REST APIs for Accounts in EazyBank",
    description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE account details"
)
public class AccountsController {

    IAccountsService accountsService;

    @Operation(
        summary = "Creates new customer account",
        description = "Creates new account for the given customer details"
    )
    @ApiResponse(
        responseCode = "201",
        description = "HTTP Status CREATED"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        accountsService.createAccount(customerDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(AccountConstants.MESSAGE_201, AccountConstants.STATUS_201));
    }

    @Operation(
        summary = "Gets list of Customers",
        description = "Provides list of all existing customers in the database"
    )
    @ApiResponse(
        responseCode = "301",
        description = "HTTP Status FOUND"
    )
    @GetMapping("/customers")
    public ResponseEntity<Iterable<CustomerDto>> getAllCustomers(){
        Iterable<CustomerDto> customers = accountsService.getAllCustomers();
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .body(customers);
    }

    @Operation(
        summary = "Get Customer Details by Customer Mobile number",
        description = "Provides customer details based on mobile number"
    )
    @ApiResponse(
        responseCode = "301",
        description = "HTTP Status FOUND"
    )
    @GetMapping("/customers/{mobileNumber}")
    public ResponseEntity<CustomerDto> getCustomerByPhoneNumber(
        @Pattern(regexp="($|[0-9]{10})", message = "Mobile number must be 10 digits")
        @PathVariable String mobileNumber){
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .body(accountsService.getCustomerByPhoneNumber(mobileNumber));
    }
    @Operation(
        summary = "Gets list of Accounts",
        description = "Provides list of all existing accounts in the database"
    )
    @ApiResponse(
        responseCode = "301",
        description = "HTTP Status FOUND"
    )
    @GetMapping("/accounts")
    public ResponseEntity<Iterable<AccountsDto>> getAllAccounts(){
        Iterable<AccountsDto> accounts = accountsService.getAllAccounts();
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .body(accounts);
    }

    @Operation(
        summary = "Get Customer Details by Customer Mobile number",
        description = "Provides customer details based on mobile number"
    )
    @ApiResponse(
        responseCode = "301",
        description = "HTTP Status FOUND"
    )
    @GetMapping("/accounts/{customerName}")
    public ResponseEntity<AccountsDto> getAccountByCustomerName(
        @Size(min = 3, max = 30, message="Name should not be less than 3 or more than 30")
        @PathVariable String customerName){
        AccountsDto account = accountsService.getAccountDetailsByCustomerName(customerName);
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .body(account);
    }

    @Operation(
        summary = "Update customer account",
        description = "Updates customer account with the provided updated customer details"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status SUCCESSFUL"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status INTERNAL_SERVER_ERROR"
        )}
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCustomerandAccounts(@Valid @RequestBody CustomerDto updatedCustomer){
        boolean isUpdated = accountsService.updateCustomerandAccount(updatedCustomer);
        if(isUpdated){
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(
                        AccountConstants.STATUS_200,
                        AccountConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(
                    AccountConstants.STATUS_500,
                    AccountConstants.MESSAGE_500));
        }
    }
    @Operation(
        summary = "Delete Customer Account",
        description = "Delete customer by given mobile number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status SUCCESSFUL"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status INTERNAL_SERVER_ERROR"
        )}
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteByCustomerId(
        @Pattern(regexp="($|[0-9]{10})", message = "Mobile number must be 10 digits")
        @RequestParam String mobileNumber){
        boolean isDeleted = accountsService.deleteCustomerByMobileNumber(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(
                    AccountConstants.STATUS_200,
                    AccountConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(
                    AccountConstants.STATUS_500,
                    AccountConstants.MESSAGE_500));
        }
    }
}
