package com.microservice.accounts.mapper;

import com.microservice.accounts.Dto.AccountDto;
import com.microservice.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountDto mapToAccountDto(Accounts accounts,AccountDto accountDto){
        accountDto.setAccountType(accounts.getAccountType());
        accountDto.setAddress(accounts.getAddress());
        accountDto.setAccountNumber(accounts.getAccountNumber());

        return accountDto;
    }

    public static Accounts mapToAccounts(AccountDto accountDto,Accounts accounts){
        accounts.setAccountNumber(accountDto.getAccountNumber());
        accounts.setAccountType(accountDto.getAccountType());
        accounts.setAddress(accountDto.getAddress());

        return accounts;
    }
}
