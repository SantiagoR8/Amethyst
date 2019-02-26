package com.example.amethyst.service;

import java.util.List;

import com.example.amethyst.model.Evaluation;

public interface EvaluationService {

	List<Evaluation> findAll();
	Evaluation getEvaluationById(Long id);
	Evaluation saveEvaluation(Evaluation evaluation);
	void deleteEvaluation(Evaluation evaluation);
	void deleteEvaluationById(Long id);
	
}
