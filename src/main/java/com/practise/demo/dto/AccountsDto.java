package com.practise.demo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty(message="AccountNumber can not be a null or empty")
    @Pattern(regexp="(^S|[0-9]{11})", message="Account number must be 11 digits")
    private Long accountNumber;

    @NotEmpty(message="Account Type must not be empty")
    private String accountType;

    @NotEmpty(message="Branch Address should not be empty")
    private String branchAddress;
}
