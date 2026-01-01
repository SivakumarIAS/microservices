package com.microservice.accounts.controller;

import com.microservice.accounts.Dto.AccountDto;
import com.microservice.accounts.Dto.CustomerDto;
import com.microservice.accounts.Dto.ResponseDto;
import com.microservice.accounts.constants.AccountsConstant;
import com.microservice.accounts.entity.Customer;
import com.microservice.accounts.service.impl.AccountServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/accounts",produces ={MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountsController {

    private AccountServiceImpl accountService;


    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){

        accountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstant.STATUS_201,AccountsConstant.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchCustomer(@RequestParam
                                                         @Pattern(regexp = "(^$|[0-9]{10})",
                                                                 message = "Mobile Number Should contain only Numeric data")
                                                         String mobile){

        CustomerDto customerDto=accountService.fetchCustomer(mobile);

//        return new ResponseEntity<>(customerDto,HttpStatus.FOUND);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(customerDto);

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated=accountService.updateAccount(customerDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstant.STATUS_200,AccountsConstant.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstant.STATUS_500,AccountsConstant.MESSAGE_500));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCustomer(@RequestParam
                                                          @Pattern(regexp = "(^$|[0-9]{10})",
                                                                  message = "Mobile Number Should contain only Numeric data")
                                                          String mobile){
        boolean isDeleted= accountService.deleteCustomer(mobile);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstant.STATUS_200,AccountsConstant.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstant.STATUS_500,AccountsConstant.MESSAGE_500));
        }
    }



}
