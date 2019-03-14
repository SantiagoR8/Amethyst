package com.example.amethyst.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "options")
public class Option {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "option_id")
	private Long id;
	
	@Version
	private Integer version;
	
	private String text;
	private boolean isCorrect;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "question_id", nullable = false)
	private Question question;	

}
