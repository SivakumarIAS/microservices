package com.microservice.accounts.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.annotation.Native;

@Data
public class AccountDto {

    @NotEmpty(message = "Account Number shouldn't be Empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Account Number should contain Only Numeric values up to 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "Account Type Shouldn't be Empty")
    private String AccountType;

    @NotEmpty(message = "Address value shouldn't be Empty")
    private String address;
}
