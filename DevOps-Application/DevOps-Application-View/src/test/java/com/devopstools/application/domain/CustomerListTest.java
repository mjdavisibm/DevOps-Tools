package com.devopstools.application.domain;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devopstools.application.view.CustomerJSONLoader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
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
			for (Customer c: customers) {
				log.debug(c.toPrettyPrintJson());
			}
		}
		assertTrue(customers.size() == 2);
	}

}
