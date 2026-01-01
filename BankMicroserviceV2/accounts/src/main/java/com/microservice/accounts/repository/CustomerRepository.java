package com.microservice.accounts.repository;

import com.microservice.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByMobile(String mobileNumber);

    Optional<Customer>  findByCustomerId(Long customerId);
}
