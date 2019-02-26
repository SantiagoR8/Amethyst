package com.example.amethyst.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.amethyst.model.Evaluation;
import com.example.amethyst.repository.EvaluationRepository;
import com.example.amethyst.repository.exception.EvaluationNotFoundException;

@Service
public class EvaluationServiceImpl implements EvaluationService {
	
	@Autowired
	EvaluationRepository testRepository;

	@Override
	public List<Evaluation> findAll(){
		return testRepository.findAll();
	}

	@Override
	public Evaluation getEvaluationById(Long id) {
		return testRepository.findById(id)
				.orElseThrow(() -> new EvaluationNotFoundException(id));
	}

	@Override
	public Evaluation saveEvaluation(Evaluation evaluation) {
		return testRepository.save(evaluation);
	}

	@Override
	public void deleteEvaluation(Evaluation evaluation) {
		testRepository.delete(evaluation);
	}

	@Override
	public void deleteEvaluationById(Long id) {
		testRepository.deleteById(id);
	}
}
