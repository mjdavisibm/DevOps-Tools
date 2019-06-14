package devopsTools.application.rest;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import devopsTools.application.domain.Customer;
import devopsTools.application.domain.Name;
import devopsTools.application.exceptions.CustomerNotFoundException;
import devopsTools.application.service.CustomerService;

@RestController
public class CustomerRestController {
	
	@Autowired
	private CustomerService service;

	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path = "/hello-world-bean")
	public Customer helloCustomer() {
		return new Customer();
	}
	
	@GetMapping(path = "/customer/{name}")
	public Customer getCustomer(@Valid @PathVariable String name) {
		Name n = new Name(name, "", "", "");
		Customer c = service.findByName(n);
		if (c == null)
			throw new CustomerNotFoundException("name-" + name);
		return c;
		
	}
	
	@PostMapping(path = "/customer")
	public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
		Customer c = service.createCustomer(customer);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(c.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	 
	

}
