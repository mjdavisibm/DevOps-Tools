package devopsTools.application.service.H2Impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import devopsTools.application.service.CustomerService;

public class ConfigureH2ServiceImpl {

	@Bean
	public CustomerService customerService() {
		return new CustomerServiceH2Impl();
	}
	
}
