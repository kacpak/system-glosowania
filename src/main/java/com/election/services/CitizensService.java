package com.election.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.election.domain.Citizens;
import com.election.repositories.CitizensRepository;

public class CitizensService {
	
	private Logger log = LoggerFactory.getLogger(CitizensService.class);
	
	@Autowired
	private CitizensRepository citizensRepository;
	
	public boolean isCitizen(String pesel, String idNumber) {
		int count = citizensRepository.countByPeselAndIdnumber(pesel, idNumber);
		if(count > 0)
			return true;
		return false;
	}
	
	public void addCitizen(String pesel, String idNumber) {
		Citizens citizen = new Citizens();
		citizen.setPeselNumber(pesel);
		citizen.setIdNumber(idNumber);
		citizensRepository.save(citizen);
	}
}
