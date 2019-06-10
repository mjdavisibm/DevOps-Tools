package devopsTools.application.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import devopsTools.application.domain.Address;
import devopsTools.application.domain.Customer;
import devopsTools.application.domain.Name;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	public Customer findByName(Name name);	

}
