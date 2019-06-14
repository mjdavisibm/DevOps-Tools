package devopsTools.application;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import devopsTools.application.domain.Customer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CustomerTest {

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
