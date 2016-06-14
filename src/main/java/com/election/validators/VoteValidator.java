package com.election.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.election.domain.Vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VoteValidator implements Validator {
	
	private Logger log = LoggerFactory.getLogger(VoteValidator.class);

	@Override
	public void validate(Object target, Errors errors) {
		log.info("Waliduję głos");
		
		Vote vote = (Vote) target;		
		if (vote == null) {
			errors.rejectValue("candidate", "noVote");
			return;
		}
		
		if (!Vote.Candidates.containsKey(vote.getCandidate()))
			errors.rejectValue("candidate", "invalidVote");
		
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Vote.class.equals(clazz);
	}

}
