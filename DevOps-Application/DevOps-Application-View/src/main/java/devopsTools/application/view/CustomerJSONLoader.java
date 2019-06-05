package devopsTools.application.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import devopsTools.application.domain.Customer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerJSONLoader {

	private static List<Customer> allObjs;
	
	/*
	 * Reads a JSON file to populate a list of customers;
	 */
	public static List<Customer> loadJSONData() {
		if (allObjs == null) {
			String filename = "/json/Customers.json";
			log.trace("Loading '" + Customer.class + "' from Json file: " + filename);
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Customer>> typeReference = new TypeReference<List<Customer>>() {
			};
			InputStream inputStream = TypeReference.class.getResourceAsStream(filename);
			try {
				allObjs = mapper.readValue(inputStream, typeReference);
				if (log.isTraceEnabled()) {
					for (Customer obj : allObjs) {
						log.trace("Line Item: " + obj);
					}
				}
			} catch (IOException e) {
				log.warn("Unable to load JSON file: " + filename + "\n Error Message: " + e.getMessage());
			}
		}
		return allObjs;
	}

}
