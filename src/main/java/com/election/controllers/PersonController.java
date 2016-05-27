package com.election.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.election.domain.Person;
import com.election.services.PersonService;


@Controller
public class PersonController {
	
	private Logger log = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	private PersonService personService;
	
	
	@RequestMapping(value = "person/save", method = RequestMethod.POST)
	public String savePerson(Model model, @Valid Person person, BindingResult bindingResult) throws IOException {
		
		if (bindingResult.hasErrors()) { // walidacja
			
			model.addAttribute("person", person);
			
			return "index";  /*- wraca na strone startową */
		}
		
		personService.saveTestObject(person);
		log.info("person: " + person.toString());
	
		return "redirect:/" + "index/";
	
	}
	
	
	@RequestMapping("index")
	String index(Model model) {
		Person person = new Person();
		model.addAttribute("person", new Person());
		log.info("person: " + person.toString());
		return "index";
	}
	
	@RequestMapping("")
	String indexT(Model model) {
		Person person = new Person();
		model.addAttribute("person", new Person());
		log.info("person: " + person.toString());
		return "index";
	}
	
}
