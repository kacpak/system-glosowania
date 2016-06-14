package com.election.services;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.election.domain.Vote;
import com.election.repositories.VoteRepository;

@Service
public class VoteService {

	private Logger log = LoggerFactory.getLogger(VoteService.class);
	
	@Autowired	
	private VoteRepository voteRepository;
	
	public void saveVote(Vote vote) {
		//log.info("Saving vote: " + vote.toString());
		log.info("Saving vote...");
		voteRepository.save(vote);
	}
	
	public Iterable<Vote> getAllVotes() {
		return voteRepository.findAll();
	}
	
	public Map<Integer, Long> getCountedVotes() {
		Map<Integer, Long> votesMap = new HashMap<>();
		for(Integer id : Vote.Candidates.keySet()) {
			votesMap.put(id, voteRepository.countByCandidate(id));
		}
		
		return votesMap;
	}

}
