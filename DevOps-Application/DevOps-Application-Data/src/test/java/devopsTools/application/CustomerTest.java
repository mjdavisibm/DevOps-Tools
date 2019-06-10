package devopsTools.application;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import devopsTools.application.domain.Customer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CustomerTest {

	@Autowired
	protected TestEntityManager entityManager;

	/*
	 * Used to hold a test customer that can be used in tests
	 */
	protected Customer testCustomer;
	protected int testCount;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	protected List<Customer> getTestCustomers() {
		log.trace("getting test customer from JSON");
		List<Customer> customers = CustomerJSONLoader.loadJSONData();
		testCustomer = customers.get(0);
		testCount = customers.size();
		return customers;
	}

	protected void populateCustomerDB() {
		log.trace("Populating test data into repository through entityManager");
		List<Customer> customers = getTestCustomers();
		for (Customer c : customers) {
			entityManager.persist(c);
		}
		entityManager.flush();
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
