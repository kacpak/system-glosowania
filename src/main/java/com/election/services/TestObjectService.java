package com.election.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.election.domain.TestObject;
import com.election.repositiories.TestObjectRepository;

@Service
public class TestObjectService {

	
	@Autowired
	private TestObjectRepository testObjectRepository;
	
	
	
	public void saveTestObject(TestObject testObject)
	{
		testObjectRepository.save(testObject);
	}
	
}
