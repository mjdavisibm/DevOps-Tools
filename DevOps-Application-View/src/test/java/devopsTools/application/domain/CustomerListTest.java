package devopsTools.application.domain;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import devopsTools.application.view.CustomerJSONLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerListTest {

	private List<Customer> customers = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		customers = CustomerJSONLoader.loadJSONData();
	}

	@Test
	public void test() {
		if (log.isDebugEnabled()) {
	        ObjectMapper mapper = new ObjectMapper();
			for (Customer c: customers) {				
		        try {
		            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(c);
					log.debug("Customer: " + prettyJson);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}
		}
		assertTrue(customers.size() == 1);
	}

}
