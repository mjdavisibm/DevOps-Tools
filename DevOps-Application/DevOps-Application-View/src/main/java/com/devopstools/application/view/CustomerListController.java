package com.devopstools.application.view;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.devopstools.application.domain.Address;
import com.devopstools.application.domain.Address.State;
import com.devopstools.application.domain.Customer;
import com.devopstools.application.domain.Customer.Gender;
import com.devopstools.application.domain.Name;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class CustomerListController {

	@Autowired
	private CustomerRepository customerRepo;

	@GetMapping("/")
	public String showCustomerForm(Model model) {
		model.addAttribute("customers", customerRepo.getAll());
		log.info("Customers Loaded from Database");
		
		Customer dummyCustomer = new Customer(
				new Name("DummyFirst", "DummyMiddle", "DummyLast","Preferred"),
				Gender.FEMALE,
				new Date(),
				new Address("1 Dummy Street", "Stourbridge", State.ALASKA, "12355"));
		model.addAttribute("customer", dummyCustomer);
		return "customerList";
	}

	@RequestMapping(value = "/", params = { "addCustomer" })
	public String addCustomer(final Customer customer, final BindingResult bindingResult, ModelMap model) {
		log.trace("Adding new customer: " + customer);
		Customer dummyCustomer = new Customer(
				new Name("DummyFirst", "DummyMiddle", "DummyLast","Preferred"),
				Gender.FEMALE,
				new Date(),
				new Address("1 Dummy Street", "Stourbridge", State.ALASKA, "12355"));
		model.addAttribute("chosenCustomer", dummyCustomer);
		return "redirect:/editCustomer";
	}

	@RequestMapping(value = "/", params = { "editCustomer" })
	public String editCustomer(final Customer customer, final BindingResult bindingResult, ModelMap model) {
		log.trace("Editing customer: " + customer);
		return "editCustomer";
	}

	@RequestMapping(value = "/", params = { "deleteCustomer" })
	public String deleteCustomer(final Customer customer, final BindingResult bindingResult,
			final HttpServletRequest req) {

		// customer = req.getParameter("deleteCustomer");
		log.trace("Deleting customer: " + customer);
		customerRepo.delete(customer);
		return "customerList";
	}

//	@PostMapping
//	public String processOrder(@ModelAttribute Customer customer, Errors errors) {
//
//		log.info("Customer submitted: " + customer);
//		return "redirect:/editCustomer";
//	}

}