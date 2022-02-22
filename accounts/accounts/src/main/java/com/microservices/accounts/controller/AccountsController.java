package com.microservices.accounts.controller;

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
import com.microservices.accounts.model.Customer;
import com.microservices.accounts.model.Properties;
import com.microservices.accounts.repository.AccountsRepository;

@RestController
public class AccountsController {
	
	@Autowired
	private AccountsRepository accountRepository;
	 
	@Autowired
	AccountsServiceConfig accountsConfig;
	
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

}
