package com.election.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.election.controllers.PersonController;
import com.election.domain.Person;

@Component
public class PeselValidator implements Validator {

	private Logger log = LoggerFactory.getLogger(PersonController.class);

	@Override
	public void validate(Object target, Errors errors) {

		Person person = (Person) target;

		if (person.getPeselNumber().matches("\\d{11}")) {
			int month = Integer.parseInt(person.getPeselNumber().substring(2, 4));
			log.info("month " + month);
			
		
			if(month > 80){
				errors.rejectValue("peselNumber", "month>80");
			}
			
			
			
		}


	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz) || Person.class.equals(clazz);
	}

}
