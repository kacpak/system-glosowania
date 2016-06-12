package com.election.repositories;

import org.springframework.data.repository.CrudRepository;

import com.election.domain.Vote;

public interface VoteRepository extends CrudRepository<Vote, Integer> {
	Long countByCandidate(Integer candidate);
}