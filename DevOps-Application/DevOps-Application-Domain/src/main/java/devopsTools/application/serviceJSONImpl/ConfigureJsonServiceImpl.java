package devopsTools.application.serviceJSONImpl;

import org.springframework.context.annotation.Bean;

import devopsTools.application.service.CustomerService;

public class ConfigureJsonServiceImpl {

	@Bean
	public CustomerService customerService() {
		return new CustomerServiceJsonImpl();
	}
		
}
