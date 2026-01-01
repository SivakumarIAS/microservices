package com.microservice.accounts.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Name shouldn't be Null")
    @Size(max = 30,min = 5,message = "Name shouldn't be less 5 or grater than 30")
    private String name;

    @Email(message = "Invalid Email format")
    @NotEmpty(message = "Email can't be empty")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number should contain only Numbers upto 10 digits")
    private String mobile;

    private AccountDto accountDto;
}
