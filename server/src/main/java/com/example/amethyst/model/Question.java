package com.example.amethyst.model;

import java.util.HashSet;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"evaluations"})
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
	@JsonIgnore
	@ManyToMany(mappedBy = "questions")
	private Set<Evaluation> evaluations = new HashSet<>();
	
	@JsonManagedReference
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	private Set<Option> options;

}
