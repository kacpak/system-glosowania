package com.election.repositories;
import org.springframework.data.repository.CrudRepository;

import com.election.domain.Citizens;

public interface CitizensRepository extends CrudRepository<Citizens, Integer> {
	int countByPeselAndIdnumber(String pesel, String idnumber);
}
