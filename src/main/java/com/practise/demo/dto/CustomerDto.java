package com.practise.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {

    @NotEmpty(message="Name field Cannot be empty")
    @Size(min = 3, max = 30, message="Name should not be less than 3 or more than 30")
    private String name;

    @NotEmpty(message="Email field should not be empty")
    @Email(message="Email should be valid value")
    private String email;

    @NotEmpty(message="Mobile number field should not be empty")
    @Pattern(regexp="($|[0-9]{10})", message="Mobile number must be 10 digits")
    private String mobileNumber;

    private AccountsDto account;
}
