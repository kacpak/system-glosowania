package com.election.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp="\\d{11}")
	private String peselNumber;
	
	@Column(unique = true)
	@Size(min = 9, max = 9)
	@Pattern(regexp="[A-Za-z]{3}\\d{6}")
	@NotBlank
	private String idNumber;
	
	private boolean voted;

}
