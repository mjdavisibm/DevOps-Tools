package devopsTools.application.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import devopsTools.application.CustomerTest;
import devopsTools.application.domain.Address;
import devopsTools.application.domain.Address.State;
import devopsTools.application.domain.Customer;
import devopsTools.application.domain.Name;

/*
 * Test that the data persistence layer is working
 * 1) It uses the domain layer objects
 * 2) Tests the CRUD level
 * 		Creates - Creates a Customer
 * 		Read - Read all Customer and Individual Customer
 * 		Update - Test different forms of update in preparation for PUT/PATCH
 * 			PUT - Update entire Customer Data, therefore entire Customer object is needed. 
 * 			PATCH - Updates a piece of the Customer, e.g. just the Address or Name
 * 		Delete - Delete a Customer using just Id
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerDataTest extends CustomerTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	// Tests Create of CRUD
	public void create_test() {
		// Saves all Test customers and confirms count
		List<Customer> customers = getTestCustomers();
		customerRepository.saveAll(customers);

		assertTrue(customerRepository.count() == testCount);	
		
		// Additional Test - Finds the test Customer
		Customer c = customerRepository.findByName(testCustomer.getName());
		assertThat(c.getName()).isEqualTo(testCustomer.getName());
	}

	@Test
	// Tests Read of CRUD
	public void read_test() {
		// Add all test customers to the repository
		populateCustomerDB();
		
		// Read all test Customers from repository and confirm they match the count
		List<Customer> customers = (List<Customer>) customerRepository.findAll();		
		assertTrue(customers.size() == testCount);
		
		// Get the Test Customer by Id then Name from the repository
		Optional<Customer> c = customerRepository.findById(testCustomer.getId());
		assertThat(c != null);
		assertThat(c).isEqualTo(testCustomer);
		
		Customer cust = customerRepository.findByName(testCustomer.getName());
		assertThat(cust).isEqualTo(testCustomer);
	}

	@Test
	// Tests Update of CRUD
	public void update_test() {
		// Add all test customers to the repository
		populateCustomerDB();
		
		// Reads the entire Customer, changes the address and Updates the entire customer
		Customer c = customerRepository.findByName(testCustomer.getName());
		Address a = new Address("100 Changed Address", "Change Me", State.CALIFORNIA, "99999");
		c.setAddress(a);
		customerRepository.save(c);
//
//		// Using the same customer updates the Name and triggers that save
//		Name name = new Name("Test", "My", "Name", "Tester");
//		customerRepository.save(c.getId(), name);
//		
//		Optional<Customer> updateC = customerRepository.findById(c.getId());
//		assertThat(updateC != null);
//		c = updateC.get();
//		assertThat(c.getName()).isEqualTo(name);

	}

	@Test
	//Test Delete of CRUD
	public void delete_test() {
		// Add all test customers to the repository
		populateCustomerDB();
		// Delete the testCustomer and ensure count has gone down by one
		customerRepository.delete(testCustomer);
		assertTrue(customerRepository.count() == testCount - 1);
	}

}
