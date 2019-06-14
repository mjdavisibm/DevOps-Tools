package devopsTools.application.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import devopsTools.application.CustomerTest;
import devopsTools.application.domain.Address;
import devopsTools.application.domain.Address.State;
import devopsTools.application.domain.Customer;
import lombok.extern.slf4j.Slf4j;

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

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerDataTest extends CustomerTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	@DirtiesContext
	// Tests Create of CRUD
	public void create_test() {
		// Saves all Test customers and confirms count
		customerRepository.deleteAll();
		List<Customer> customers = getTestCustomers();
		customerRepository.saveAll(customers);

		assertTrue(customerRepository.count() == testCount);	
		
		// Additional Test - Finds the test Customer
		Optional<Customer> cust = customerRepository.findByName(testCustomer.getName());
		assertTrue(cust.isPresent());
		assertThat(cust.get().getName()).isEqualTo(testCustomer.getName());
	}

	@Test
	// Tests Read of CRUD
	public void read_test() {
		getTestCustomers();
		
		// Read all test Customers from repository and confirm they match the count
		List<Customer> customers = (List<Customer>) customerRepository.findAll();		
		assertTrue(customers.size() > 0);
		
		// Get the Test Customer by Id then Name from the repository
		Optional<Customer> c = customerRepository.findById(testCustomer.getId());
		assertThat(c != null);
		log.trace("test Customer: {}", testCustomer.toPrettyPrintJson());
		log.trace("From DB Customer: {}",c.get().toPrettyPrintJson());
		
		assertThat(c.get()).isEqualTo(testCustomer);
		
		Optional<Customer> cust = customerRepository.findByName(testCustomer.getName());
		assertTrue(cust.isPresent());
		assertThat(cust.get()).isEqualTo(testCustomer);
	}

	@Test
	@DirtiesContext
	// Tests Update of CRUD
	public void update_test() {
		getTestCustomers();
		long count = customerRepository.count();
		log.trace("Number of customers before update: {}", count);
		
		// Reads the entire Customer, changes the address and Updates the entire customer
		Optional<Customer> ocust = customerRepository.findById(testCustomer.getId());
		assertTrue(ocust.isPresent());
		Customer c = ocust.get();
		Address a = new Address("100 Changed Address", "Change Me", State.CALIFORNIA, "99999");
		c.setAddress(a);
		c = customerRepository.save(c);
			
		long newCount = customerRepository.count();
		log.trace("Number of Customers after update: {}", newCount);
		
		Optional<Customer> ocust2 = customerRepository.findById(testCustomer.getId());
		assertTrue(ocust2.isPresent());
		log.trace("Changed Customer: {}",ocust2.get());
		assertThat(ocust2.get().getAddress()).isEqualTo(a);	
		
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
	@DirtiesContext
	//Test Delete of CRUD
	public void delete_test() {
		getTestCustomers();
		long count = customerRepository.count();
		// Delete the testCustomer and ensure count has gone down by one
		customerRepository.delete(testCustomer);
		assertTrue(customerRepository.count() == count - 1);
	}

}
