package com.microservices.accounts.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservices.accounts.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>{
  
}
