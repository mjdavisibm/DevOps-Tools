package com.devopstools.application.view;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.devopstools.application.domain.Customer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CustomerRepository {

	private List<Customer> customers;
	
	public CustomerRepository() {
		super();
		
	}
	
	public List<Customer> getAll() {
		customers = CustomerJSONLoader.loadJSONData();
		if (log.isTraceEnabled()) {
			log.trace("Loaded Customes");
			for (Customer c : customers) {
				log.trace("Customer" + c);
			}
		}
		return customers;
	}
	
	public List<Customer> add(Customer c) {
		customers.add(c);
		return customers;
	}
	
	public List<Customer> delete(Customer c) {
		customers.remove(c);
		return customers;
	}
	
	
	public List<Customer> update(Customer c) {
		customers.remove(c);
		customers.add(c);
		return customers;
	}

}
