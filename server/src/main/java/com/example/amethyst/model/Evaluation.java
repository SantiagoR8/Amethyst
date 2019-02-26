package com.example.amethyst.model;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "evaluations")
public class Evaluation {

	@Id
	@GeneratedValue
	@Column(name = "evaluation_id")
	private Long id;
	@Version
	private Integer version;
	
	private String name;
	
	@ManyToMany
	@JoinTable(
		joinColumns = @JoinColumn(name = "evaluation_id"),
		inverseJoinColumns = @JoinColumn(name = "question_id")
	)
	private Set<Question> questions;
	
	@JsonCreator
	public Evaluation(String name) {
		this.name = name;
	}
	
}
