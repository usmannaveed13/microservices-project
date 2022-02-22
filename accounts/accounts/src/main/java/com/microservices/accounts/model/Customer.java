package com.microservices.accounts.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class Customer {
	@Column(name="customer_id")
	@Id
	private int customerId;
	@Column(name="name")
	private String customerName;
	@Column(name="email")
	private String customerEmail;
	@Column(name="mobile_number")
	private String customerMobileNumber;
	@Column(name="create_dt")
	private LocalDate createDt;

}
