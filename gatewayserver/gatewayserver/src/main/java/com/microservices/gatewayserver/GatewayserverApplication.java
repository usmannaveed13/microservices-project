package com.microservices.gatewayserver;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}
	
	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/microservices/accounts/**")
						.filters(f -> f.rewritePath("microservices/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Reponse-Time", new Date().toString()))
						.uri("lb://ACCOUNTS")).
				route(p -> p
						.path("/microservices/loans/**")
						.filters(f -> f.rewritePath("microservices/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Reponse-Time", new Date().toString()))
						.uri("lb://LOANS")).
				route(p -> p
						.path("/microservices/cards/**")
						.filters(f -> f.rewritePath("microservices/cards/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Reponse-Time", new Date().toString()))
						.uri("lb://CARDS")).build();
	}
}
