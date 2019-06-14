package devopsTools.application.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import devopsTools.application.CustomerTest;
import devopsTools.application.DevOpsApplicationDataApplication;
import devopsTools.application.domain.Address;
import devopsTools.application.domain.Address.State;
import devopsTools.application.domain.Customer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DevOpsApplicationDataApplication.class)
public class CustomerRepositoryTest extends CustomerTest{
	
	@Autowired
	CustomerRepository repo;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void findById_CustomerExists() {
		getTestCustomers();
		long id = testCustomer.getId();
		Optional<Customer> c = repo.findById(id);
		assertTrue(c.isPresent());
	}
	
	@Test
	public void findById_Customer_NotExist() {
		Optional<Customer> c = repo.findById(77777L);
		assertFalse(c.isPresent());
	}
	
	@Test
	public void findAll() {
		List<Customer> c = (List<Customer>) repo.findAll();
		assertTrue(c.size() > 0);
	}
	
	@Test
	@DirtiesContext
	public void sortedCustomers() {
		Sort sort = new Sort(Sort.Direction.DESC, "name.lastName");
		log.info("Sorted Courses -> {}", repo.findAll(sort));
	}
	
	@Test
	public void pagination() {
		PageRequest pageRequest = PageRequest.of(0,3);
		Page<Customer> firstPage = repo.findAll(pageRequest);
		log.info("First Page -> {}", firstPage.getContent());
		
		Pageable secondPageable = firstPage.nextPageable();
		Page<Customer> secondPage = repo.findAll(secondPageable);
		log.info("Second Page -> {}", secondPage.getContent());
		
	}
	
	@Test
	public void findByName() {
		getTestCustomers();
		Optional<Customer> c =  repo.findByName(testCustomer.getName());
		assertTrue(c.isPresent());
		assertEquals(c.get(),testCustomer);
	}

	@Test
	@DirtiesContext
	public void save() {
		getTestCustomers();		
		long count = repo.count();
		log.trace("Number of customers before save: {}", count);

		// Ensure the Database creates a new entity
		testCustomer.setId(0L);
		Customer c = repo.save(testCustomer);
		log.trace("Saved Customer: {}",c);
		assertEquals(c,testCustomer);
		
		long newCount = repo.count();
		log.trace("Number of Customers after save: {}", newCount);
		assertTrue(newCount == count + 1 );
		for (Customer c1 : repo.findAll()) {
			log.trace("Customer: {}", c1.toPrettyPrintJson());
		}
	}
	
	@Test
	@DirtiesContext
	public void update() {
		getTestCustomers();
		long count = repo.count();
		log.trace("Number of customers before update: {}", count);
		
		// Retrieve a Customer, change Address, update DB
		Optional<Customer> oc = repo.findById(testCustomer.getId());
		assertTrue(oc.isPresent());
		Customer c = oc.get();
		Address addr = new Address("100 I have been changed","somewhere",State.CALIFORNIA,"00000");
		c.setAddress(addr);
		Customer savedC = repo.save(c);
		
		// Retrieve Changed Customer
		log.trace("Confirming Address has changed");
		Optional<Customer> ocust2 = repo.findById(savedC.getId());
		assertTrue(ocust2.isPresent());
		log.trace("Changed Customer: {}",ocust2.get());
		assertThat(ocust2.get().getAddress()).isEqualTo(addr);	

		long newCount = repo.count();
		log.trace("Number of Customers after update: {}", newCount);
		assertTrue(newCount == count);
		for (Customer c1 : repo.findAll()) {
			log.trace("Customer: {}", c1.toPrettyPrintJson());
		}
	}
	

}
