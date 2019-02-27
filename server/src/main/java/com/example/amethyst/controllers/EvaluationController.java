package com.example.amethyst.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.example.amethyst.model.Evaluation;
import com.example.amethyst.service.EvaluationService;

@RestController
public class EvaluationController {

	@Autowired
	EvaluationService evaluationService;
	
	@GetMapping("/evaluations")
	public List<Evaluation> getAllEvaluations(){
		return evaluationService.findAll();		
	}
	
	@GetMapping("/evaluations/{id}")
	public Evaluation getEvaluationById(@PathVariable(value="id") Long id) {
		return evaluationService.getEvaluationById(id);
	}
	
	@PostMapping("/evaluations")
	@ResponseStatus(HttpStatus.CREATED)
	public Evaluation createEvaluation(@RequestBody Evaluation evaluation) {
		return evaluationService.saveEvaluation(evaluation);				
	}
	
	@PutMapping(value = "/evaluations/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateEvaluation(@PathVariable("id") Long id, @RequestBody Evaluation evaluation) {
		evaluationService.getEvaluationById(id);
		evaluationService.saveEvaluation(evaluation);
	}
	
	@DeleteMapping("/evaluations/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteEvaluation(@PathVariable("id") Long id) {
		evaluationService.deleteEvaluationById(id);
	}
}
