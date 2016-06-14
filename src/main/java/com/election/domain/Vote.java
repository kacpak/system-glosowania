package com.election.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Vote {
	
	public static Map<Integer, String> Candidates;
	
	static {
		Candidates = new HashMap<>();
		Candidates.put(0, "-- Głos nieważny");
		Candidates.put(1, "Hilary Clinton");
		Candidates.put(2, "Donald Trump");
		Candidates.put(3, "Bernie Sanders");
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private Integer candidate = 0;
}
