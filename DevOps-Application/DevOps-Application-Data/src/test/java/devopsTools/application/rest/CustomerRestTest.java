package devopsTools.application.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import devopsTools.application.CustomerTest;
import devopsTools.application.DevOpsApplicationDataApplication;
import devopsTools.application.domain.Address;
import devopsTools.application.domain.Address.State;
import devopsTools.application.domain.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
//@WebAppConfiguration
//@WebMvcTest
@AutoConfigureTestEntityManager
@AutoConfigureMockMvc
//@ContextConfiguration(classes=DevOpsApplicationDataApplication.class)
//@DataJpaTest
//@AutoConfigureWebMvc
public class CustomerRestTest extends CustomerTest {
	
	@Autowired
	private MockMvc mvc;

	@Test
	public void getGETCustomers() throws Exception {
		populateCustomerDB();
		String uri = "/customers";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Customer[] productlist = super.mapFromJson(content, Customer[].class);
		assertTrue(productlist.length == 2);
	}

	@Test
	public void createPOSTCustomer() throws Exception {
		List<Customer> customers = getTestCustomers();

		String uri = "/customers";
		String inputJson = super.mapToJson(customers.get(0));
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Customer is created successfully");
	}

	@Test
	public void updatePUTCustomer() throws Exception {
		populateCustomerDB();
		String uri = "/customers/" + testCustomer.getId();

		Address a = new Address("100 Changed Address", "Change Me", State.CALIFORNIA, "99999");
		testCustomer.setAddress(a);
		String inputJson = super.mapToJson(testCustomer);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Customer is updated successsfully");
	}
	
	@Test
	public void updatePATCHCustomer() throws Exception {
		populateCustomerDB();
		String uri = "/customers/" + testCustomer.getId();

		Address a = new Address("72 Changed Address", "Change Me", State.CALIFORNIA, "99999");
		String inputJson = super.mapToJson(a);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Customer Address is updated successsfully");
	}

	@Test
	public void deleteDELETECustomer() throws Exception {
		populateCustomerDB();
		String uri = "/customers/" + testCustomer.getId();
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Customer is deleted successsfully");
	}

}
