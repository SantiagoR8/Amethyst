package com.example.amethyst.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "questions")
public class Question {
			
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_id")
	private Long id;
	@Version
	private Integer version;
	private String title;
	private String body;
	private String answer;
	@ManyToMany(mappedBy = "questions")
	private Set<Evaluation> evaluations;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	private Set<Option> options;
	
	public Question() {
	}

}
