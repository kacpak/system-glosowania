package com.election.controllers;

import java.io.IOException;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.election.domain.Person;
import com.election.domain.Vote;
import com.election.services.PersonService;
import com.election.services.VoteService;
import com.election.validators.IdNumberValidator;
import com.election.validators.PeselValidator;
import com.election.validators.VoteValidator;


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
	
	@Autowired
	private VoteValidator voteValidator;

	@InitBinder("person")
	protected void initPersonBinder(final WebDataBinder binder) {
		binder.addValidators(peselValidator, idNumberValidator);
	}

	@InitBinder("vote")
	protected void initVoteBinder(final WebDataBinder binder) {
		binder.setValidator(voteValidator);
	}
	
	@RequestMapping(value={"", "index"})
	String index(Model model) {
		log.info("Otworzono stronę główną");
		
		model.addAttribute("person", new Person());
		model.addAttribute("vote", new Vote());
		model.addAttribute("candidates", Vote.Candidates);
		
		return "index";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String savePerson(Model model, @Valid Person person, BindingResult personBindingResult, @Valid Vote vote, BindingResult voteBindingResult) throws IOException {		
		// Jeśli walidacja nie przeszła pomyślnie
		if (personBindingResult.hasErrors() || voteBindingResult.hasErrors()) {
			log.info("Błędy mapowania osoby: " + personBindingResult.getAllErrors());
			log.info("Błędy mapowania głosu: " + voteBindingResult.getAllErrors());
			
			model.addAttribute("person", person);
			model.addAttribute("vote", vote);
			model.addAttribute("candidates", Vote.Candidates);
			
			// Wyświetl stronę główną
			return "index";
		}
		
		personService.saveTestObject(person);
		log.info("Saved person to database");
		
		voteService.saveVote(vote);
		log.info("Saved vote to database");
		
		// Przejdź do strony potwierdzenia zagłosowania
		return "redirect:/voted";
	}
	
	@RequestMapping(value = "voted", method = RequestMethod.GET)
	public String voted(Model model) throws IOException {
		log.info("Wyświetlono podziękowanie za oddany głos");
		return "personVoted";
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
