package com.election.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import com.election.repositories.VoteRepository;
import com.election.services.PersonService;
import com.election.services.VoteService;
import com.election.validators.IdNumberValidator;
import com.election.validators.PeselValidator;


@Controller
public class PageController {
	
	private Logger log = LoggerFactory.getLogger(PageController.class);
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private VoteService voteService;
	
	@Autowired
	private PeselValidator peselValidator;
	
	@Autowired
	private IdNumberValidator idNumberValidator;	

	@InitBinder("person")
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(peselValidator, idNumberValidator);
	}
	
	@RequestMapping(value = "person/save", method = RequestMethod.POST)
	public String savePerson(Model model, @Valid Person person, Vote vote, BindingResult bindingResult) throws IOException {		
		// Jeśli walidacja osoby nie przeszła
		if (bindingResult.hasErrors()) {
			model.addAttribute("person", person);
			model.addAttribute("vote", vote);
			log.info("Walidacja niepoprawna: " + bindingResult.getAllErrors().toString());
			
			// Powrót na stronę główną
			return "index";
		}
		
		// Zmień numer dowodu na lowercase
		person.setIdNumber(person.getIdNumber().toLowerCase());
		
		// Dodatkowa walidacja pól
		peselValidator.validate(person, bindingResult);
		idNumberValidator.validate(person, bindingResult);
		
		log.info("person: " + person);
		personService.saveTestObject(person);
		
		voteService.saveVote(vote);
		log.info("Saved vote: " + vote);
		
		// Przejdź do strony głosowania
		return "redirect:/" + "person/voting/" + person.getId();
	}
	
	@RequestMapping(value = "person/voting/{id}", method = RequestMethod.GET)
	public String savePerson(Model model, @Valid Person person, BindingResult bindingResult, @PathVariable Integer id) throws IOException {		
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
		
		Vote vote = new Vote();
		model.addAttribute("vote", vote);
		
		model.addAttribute("candidates", Vote.Candidates);
		
		return "index";
	}
	
	@RequestMapping("")
	String indexT(Model model) {
		return index(model);
	}
	
	@RequestMapping(value = "votes", method = RequestMethod.GET)
	public String showVotes(Model model) {
		Map<String, Long> namedCountedVotes = new HashMap<>();
		Long totalVotes = 0L;
		for (Entry<Integer, Long> votes : voteService.getCountedVotes().entrySet()) {
			namedCountedVotes.put(Vote.Candidates.get(votes.getKey()), votes.getValue());
			totalVotes += votes.getValue();
		}
		model.addAttribute("votes", namedCountedVotes);
		model.addAttribute("totalVotes", totalVotes == 0 ? 1 : totalVotes);
		
		return "votes";
	}
}
