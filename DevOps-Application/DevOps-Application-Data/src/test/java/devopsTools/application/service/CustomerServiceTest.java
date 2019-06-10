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
	public void create_test() {
		List<Customer> customers = getTestCustomers();
		customerService.createCustomers(customers);

		assertTrue(customerService.getNumberOfCustomers() == testCount);
		Customer c = customerService.findByName(testCustomer.getName());
		assertThat(c.getName()).isEqualTo(testCustomer.getName());
	}

	@Test
	public void read_test() {
		populateCustomerDB();
		assertTrue(customerService.getNumberOfCustomers() == testCount);
		Customer c = customerService.findByName(testCustomer.getName());
		assertThat(c.getName()).isEqualTo(testCustomer.getName());
	}

	@Test
	public void update_test() {
		populateCustomerDB();
		assertTrue(customerService.getNumberOfCustomers() == testCount);

		Customer c = customerService.findByName(testCustomer.getName());
		Address a = new Address("100 Changed Address", "Change Me", State.CALIFORNIA, "99999");
		c.setAddress(a);
		customerService.updateCustomer(c);

		Customer newC = customerService.findByName(testCustomer.getName());
		assertThat(c.getName()).isEqualTo(testCustomer.getName());
		assertThat(newC.getAddress()).isEqualTo(a);
		assertTrue(customerService.getNumberOfCustomers() == testCount);
	}

	@Test
	public void delete_test() {
		populateCustomerDB();
		assertTrue(customerService.getNumberOfCustomers() == testCount);

		customerService.deleteCustomer(testCustomer);
		assertTrue(customerService.getNumberOfCustomers() == testCount - 1);
	}


}
