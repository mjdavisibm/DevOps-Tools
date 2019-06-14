package devopsTools.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import devopsTools.application.CustomerTest;
import devopsTools.application.domain.Address;
import devopsTools.application.domain.Address.State;
import devopsTools.application.domain.Customer;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceTest extends CustomerTest{

	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
  
        @Bean
        public CustomerService customerService() {
            return new CustomerServiceImpl();
        }
    }
    
	@Autowired
	private CustomerService customerService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	@DirtiesContext
	public void create_test() {
		List<Customer> customers = getTestCustomers();
		customerService.deleteAllCustomers();
		customerService.createCustomers(customers);

		assertTrue(customerService.getNumberOfCustomers() == testCount);
		Customer c = customerService.findByName(testCustomer.getName());
		assertThat(c.getName()).isEqualTo(testCustomer.getName());
	}

	@Test
	public void read_test() {
		getTestCustomers();
		Customer c = customerService.findByName(testCustomer.getName());
		assertThat(c.getName()).isEqualTo(testCustomer.getName());
	}

	@Test
	@DirtiesContext
	public void update_test() {
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
	public void delete_test() {
		getTestCustomers();
		long count = customerService.getNumberOfCustomers();

		customerService.deleteCustomer(testCustomer);
		assertTrue(customerService.getNumberOfCustomers() == count - 1);
	}


}
