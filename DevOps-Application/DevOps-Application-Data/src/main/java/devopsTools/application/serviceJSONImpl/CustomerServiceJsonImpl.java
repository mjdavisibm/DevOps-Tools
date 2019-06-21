package devopsTools.application.serviceJSONImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import devopsTools.application.domain.Customer;
import devopsTools.application.domain.Name;
import devopsTools.application.service.CustomerService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CustomerServiceJsonImpl extends ServiceJsonImpl implements CustomerService {

	private List<Customer> allObjs;
	
	protected List<Customer> getAll() {
		if (allObjs == null) {
			String filename = "Customers.json";
			TypeReference<List<Customer>> typeReference = new TypeReference<List<Customer>>() {
			};
			@SuppressWarnings("unchecked")
			List<Customer> all = (List<Customer>) (Object) getAllFromJsonFile(filename, typeReference);
			allObjs = all;
		}
		return allObjs;
	}
	
	@Override
	public boolean createCustomer(Customer customer) {
		getAll();
		return allObjs.add(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		getAll();
		allObjs.remove(customer);
		allObjs.add(customer);
	}

	@Override
	public void deleteCustomer(Customer customer) {
		getAll();
		allObjs.remove(customer);
	}

	@Override
	public List<Customer> getCustomers() {
		getAll();
		return allObjs;
	}

	@Override
	public void createCustomers(List<Customer> customers) {
		getAll();
		for (Customer c: customers)
			createCustomer(c);

	}

	@Override
	public Customer findByName(Name name) {
		getAll();
		for (Customer c : allObjs) {
			if (c.getName().equals(name))
				return c;
		}
		return null;
	}

	@Override
	public int getNumberOfCustomers() {
		getAll();
		return allObjs.size();
	}

	@Override
	public void deleteAllCustomers() {
		allObjs = new ArrayList<Customer>();
	}

}
