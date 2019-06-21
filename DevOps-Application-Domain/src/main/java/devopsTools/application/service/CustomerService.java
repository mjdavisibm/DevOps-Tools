package devopsTools.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import devopsTools.application.domain.Customer;
import devopsTools.application.domain.Name;

@Service
public interface CustomerService {
	   public abstract boolean createCustomer(Customer customer);
	   public abstract void updateCustomer(Customer customer);
	   public abstract void deleteCustomer(Customer customer);
	   public abstract void deleteAllCustomers();
	   public abstract List<Customer> getCustomers();
	   public abstract void createCustomers(List<Customer> customers);
	   
	   // Search services
	   public abstract int getNumberOfCustomers();
	   public abstract Customer findByName(Name name);
}
