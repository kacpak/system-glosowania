package com.election.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.election.domain.Person;
import com.election.domain.Vote;
import com.election.services.PersonService;
import com.election.validators.IdNumberValidator;
import com.election.validators.PeselValidator;


@Controller
public class PersonController {
	
	private Logger log = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private PeselValidator peselValidator;
	
	@Autowired
	private IdNumberValidator idNumberValidator;
	

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
	  binder.addValidators(peselValidator, idNumberValidator);
	}
	
	@RequestMapping(value = "person/save", method = RequestMethod.POST)
	public String savePerson(Model model, @Valid Person person, BindingResult bindingResult) throws IOException {
		// Jeśli walidacja osoby nie przeszła
		if (bindingResult.hasErrors()) {			
			model.addAttribute("person", person);
			
			// Powrót na stronę główną
			return "index";
		}
		
		// Zmień numer dowodu na lowercase
		person.setIdNumber(person.getIdNumber().toLowerCase());
		
		// Dodatkowa walidacja pól
		peselValidator.validate(person, bindingResult);
		idNumberValidator.validate(person, bindingResult);
		
		/* TODO zastanowić się czy potrzebne, gdy strona główna jest stroną głosowania
		// Sprawdź czy dana osoba już istnieje w bazie
		Person foundPerson = personService.findByPesel(person.getPeselNumber());
		
		// Jeśli nie to dodaj ją do bazy
		if (foundPerson == null)
			personService.saveTestObject(person);
		else
			person = foundPerson;
				
		log.info("person: " + person.toString());

		// Jeśli osoba oddała już głos to zwróć błąd
		if (person.isVoted())
			return "redirect:/error";
		*/
		
		log.info("person: " + person.toString());
		personService.saveTestObject(person);
		
		// Przejdź do strony głosowania
		return "redirect:/" + "person/voting/" + person.getId();
	}
	
	@RequestMapping(value = "person/voting/{id}", method = RequestMethod.GET)
	public String savePerson(Model model, @Valid Person person, BindingResult bindingResult, @PathVariable Integer id) throws IOException {		
		model.addAttribute("person", personService.findById(id));
		
		if (bindingResult.hasErrors()) { // walidacja			
			model.addAttribute("person", personService.findById(id));
			
			return "personVoting";  /*- wraca do widoku personVoting */
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
				
		Map<Integer, String> candidates = new LinkedHashMap<>();
		candidates.put(1,  "Trump");
		candidates.put(2,  "Hilary");		
		model.addAttribute("candidates", candidates);
		log.info("index: Added candidates");
		
		return "index";
	}
	
	@RequestMapping("")
	String indexT(Model model) {
		return index(model);
	}
	
}
