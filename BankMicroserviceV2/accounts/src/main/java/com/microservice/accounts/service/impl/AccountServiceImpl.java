package com.microservice.accounts.service.impl;

import com.microservice.accounts.Dto.AccountDto;
import com.microservice.accounts.Dto.CustomerDto;
import com.microservice.accounts.Exception.CustomerAlreadyExists;
import com.microservice.accounts.Exception.ResourceNotFoundException;
import com.microservice.accounts.constants.AccountsConstant;
import com.microservice.accounts.entity.Accounts;
import com.microservice.accounts.entity.Customer;
import com.microservice.accounts.mapper.AccountsMapper;
import com.microservice.accounts.mapper.CustomerMapper;
import com.microservice.accounts.repository.AccountsRepository;
import com.microservice.accounts.repository.CustomerRepository;
import com.microservice.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    private CustomerRepository customerRepository;

    private AccountsRepository accountsRepository;


    @Override
    public void createAccount(CustomerDto customerDto) {

            Customer customer=CustomerMapper.mapToCustomer(customerDto,new Customer());

            Optional<Customer> optionalCustomer=customerRepository.findByMobile(customer.getMobile());
            if(optionalCustomer.isPresent()){
               throw new CustomerAlreadyExists("Customer Already Exists for Mobile : "+customer.getMobile());
            }else {
                Customer savedCustomer=customerRepository.save(customer);
                accountsRepository.save(createNewAccount(savedCustomer));
            }



    }


    public Accounts createNewAccount(Customer customer){
        Accounts newAccount=new Accounts();

        long randomNumber=100000000L+ new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomNumber);
        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountType(AccountsConstant.SAVINGS);
        newAccount.setAddress(AccountsConstant.ADDRESS);

        return newAccount;
    }

    @Override
    public CustomerDto fetchCustomer(String mobile) {
        Customer customer=customerRepository.findByMobile(mobile).orElseThrow(
                ()->new ResourceNotFoundException("Customer","Mobile Number",mobile)
        );


        Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Accounts","Customer ID",customer.getCustomerId().toString())
        );

        CustomerDto customerDto=CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        AccountDto accountDto= AccountsMapper.mapToAccountDto(accounts,new AccountDto());

        customerDto.setAccountDto(accountDto);
        return customerDto;
    }

    /**
     * @param customerDto : Input
     * @return : Boolean
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated=false;
        AccountDto accountDto=customerDto.getAccountDto();
        if(accountDto != null){
            Accounts accounts=accountsRepository.findByAccountNumber(accountDto.getAccountNumber()).orElseThrow(
                    ()->new ResourceNotFoundException("Account","Account Number",accountDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountDto,accounts);
            accounts=accountsRepository.save(accounts);

            Long customerId=accounts.getCustomerId();
            Customer customer=customerRepository.findByCustomerId(customerId).orElseThrow(
                    ()->new ResourceNotFoundException("Customer","Customer Id",customerId.toString())
            );

            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);

            isUpdated=true;
        }
        return isUpdated;
    }

    /**
     * @param mobile : Input
     * @return : Boolean
     */
    @Override
    public boolean deleteCustomer(String mobile) {
        boolean isDelete=false;
        if(!mobile.isBlank()){
            Customer customer=customerRepository.findByMobile(mobile).orElseThrow(
                    ()->new ResourceNotFoundException("Customer","Mobile Number",mobile)
            );
            Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                    ()->new ResourceNotFoundException("Accounts","Customer ID",customer.getCustomerId().toString())
            );

            customerRepository.delete(customer);
//            accountsRepository.deleteByCustomerId(customer.getCustomerId());

            accountsRepository.delete(accounts);
            isDelete=true;
        }

        return isDelete;
    }


}
