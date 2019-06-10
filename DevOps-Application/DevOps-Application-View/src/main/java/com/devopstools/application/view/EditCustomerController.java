package com.devopstools.application.view;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devopstools.application.domain.Address;
import com.devopstools.application.domain.Customer;
import com.devopstools.application.domain.Name;
import com.devopstools.application.domain.Address.State;
import com.devopstools.application.domain.Customer.Gender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("editCustomer")
public class EditCustomerController {

	@Autowired
	private CustomerRepository customerRepo;

	@GetMapping
	public String editCustomerForm(Model model) {
		log.trace("EditCustomerForm");
//		Customer EDITCustomer = new Customer(new Name("EDITFirst", "EDITMiddle", "EDITLast", "Preferred"),
//				Gender.FEMALE, new Date(), new Address("1 EDIT Street", "EDIT", State.ALASKA, "00000"));
//
//		model.addAttribute("chosenCustomer", EDITCustomer);
		return "editCustomer";
	}

	@PostMapping
	public String processOrder(@Valid Customer customer, Errors errors) {
		log.info("Create Customer");
		//customerRepo.add(customer);
		return "redirect:/";
	}

}