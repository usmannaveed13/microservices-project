package com.microservices.accounts.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.microservices.accounts.model.Cards;
import com.microservices.accounts.model.Customer;

@FeignClient("cards")
public interface CardsFeignClient {
	
	@RequestMapping(method = RequestMethod.POST , value = "myCards", consumes = "application/json")
	List<Cards> getCardDetails(@RequestHeader("Microservice-correlation-id") String correlationId ,@RequestBody Customer customer);

}
