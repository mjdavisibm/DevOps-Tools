package devopsTools.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import devopsTools.application.domain.Address;
import devopsTools.application.domain.Address.State;
import devopsTools.application.domain.Customer;
import devopsTools.application.domain.CustomerTest;
import devopsTools.application.serviceJSONImpl.CustomerServiceJsonImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
//@ContextConfiguration(classes = ConfigureH2ServiceImpl.class)
//Used to force saves before gets, before update, before delete
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerJsonServiceTest extends CustomerTest {

	@TestConfiguration
	static class TestContextConfiguration {
		@Bean
		public CustomerService customerService() {
			return new CustomerServiceJsonImpl();
		}
	}
	
	@Autowired
	private CustomerService customerService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	@DirtiesContext
	public void checkA_create() throws Exception {
		List<Customer> customers = getTestCustomers();
		customerService.deleteAllCustomers();
		customerService.createCustomers(customers);

		assertTrue(customerService.getNumberOfCustomers() == testCount);
		Customer c = customerService.findByName(testCustomer.getName());
		assertThat(c.getName()).isEqualTo(testCustomer.getName());
	}

	@Test
	public void checkB_read() throws Exception {
		getTestCustomers();
		Customer c = customerService.findByName(testCustomer.getName());
		assertThat(c.getName()).isEqualTo(testCustomer.getName());
	}

	@Test
	@DirtiesContext
	public void checkC_update() throws Exception {
		getTestCustomers();

		Customer c = customerService.findByName(testCustomer.getName());
		Address a = new Address("99 Changed Address", "Change Me", State.CALIFORNIA, "99999");
		c.setAddress(a);
		customerService.updateCustomer(c);

		Customer newC = customerService.findByName(testCustomer.getName());
		assertThat(c.getName()).isEqualTo(testCustomer.getName());
		assertThat(newC.getAddress()).isEqualTo(a);
	}

	@Test
	@DirtiesContext
	public void checkD_delete() throws Exception {
		getTestCustomers();
		long count = customerService.getNumberOfCustomers();

		customerService.deleteCustomer(testCustomer);
		assertTrue(customerService.getNumberOfCustomers() == count - 1);
	}

}
