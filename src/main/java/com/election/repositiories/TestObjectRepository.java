package com.election.repositiories;
import org.springframework.data.repository.CrudRepository;

import com.election.domain.TestObject;


public interface TestObjectRepository  extends CrudRepository<TestObject, Integer>{
	
	      TestObject saveTestObject(TestObject testObject);

}
