package com.microservices.accounts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.microservices.accounts.config.AccountsServiceConfig;
import com.microservices.accounts.model.Accounts;
import com.microservices.accounts.model.Cards;
import com.microservices.accounts.model.Customer;
import com.microservices.accounts.model.CustomerDetails;
import com.microservices.accounts.model.Loans;
import com.microservices.accounts.model.Properties;
import com.microservices.accounts.repository.AccountsRepository;
import com.microservices.accounts.service.client.CardsFeignClient;
import com.microservices.accounts.service.client.LoansFeignClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class AccountsController {
	
	@Autowired
	private AccountsRepository accountRepository;
	 
	@Autowired
	AccountsServiceConfig accountsConfig;
	
	@Autowired
	private LoansFeignClient loansFeignClient;
	
	@Autowired
	private CardsFeignClient cardsFeignClient;
	
	@PostMapping("/myAccount")
	public Accounts getAccountsDetails(@RequestBody Customer customer) {
		Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId());
		if (accounts != null) {
			return accounts;
		} else {
			return null;
		}
	}
	
	
	@GetMapping("/accounts/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(accountsConfig.getMsg(), accountsConfig.getBuildVersion(),
				accountsConfig.getMailDetails(), accountsConfig.getActiveBranches());
		String jsonStr = ow.writeValueAsString(properties);
		return jsonStr;
		
	}
    
	@PostMapping("/myCustomerDetails")
//	@CircuitBreaker(name = "detailsForCustomerSupportApp", fallbackMethod = "myCustomerDetailsFallBack")
	@Retry(name= "retryForCustomerDetails", fallbackMethod = "myCustomerDetailsFallBack")
	public CustomerDetails myCustomerDetails(@RequestBody Customer customer) {
		
		Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId());
		List<Loans> loans = loansFeignClient.getLoansDetails(customer);
		List<Cards> cards = cardsFeignClient.getCardDetails(customer);
		
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setAccounts(accounts);
		customerDetails.setCards(cards);
		customerDetails.setLoans(loans);
		return customerDetails;
		
	}
	
	private CustomerDetails myCustomerDetailsFallBack(Customer customer, Throwable t) {
		Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId());
		List<Loans> loans = loansFeignClient.getLoansDetails(customer);
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setAccounts(accounts);
		customerDetails.setLoans(loans);
		return customerDetails;
	}

}
