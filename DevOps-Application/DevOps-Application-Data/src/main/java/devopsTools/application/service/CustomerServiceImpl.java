package devopsTools.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devopsTools.application.data.CustomerRepository;
import devopsTools.application.domain.Customer;
import devopsTools.application.domain.Name;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepo;

	@Override
	public void createCustomer(Customer customer) {
		customerRepo.save(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		customerRepo.save(customer);
	}

	@Override
	public void deleteCustomer(Customer customer) {
		customerRepo.delete(customer);

	}

	@Override
	public List<Customer> getCustomers() {
		return (List<Customer>) customerRepo.findAll();
	}

	@Override
	public void createCustomers(List<Customer> customers) {
		customerRepo.saveAll(customers);

	}

	@Override
	public Customer findByName(Name name) {
		return customerRepo.findByName(name);
	}

	@Override
	public int getNumberOfCustomers() {
		return (int) customerRepo.count();
	}

}
