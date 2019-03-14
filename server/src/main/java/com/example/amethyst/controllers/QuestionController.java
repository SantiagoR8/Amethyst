package com.example.amethyst.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.amethyst.model.Question;
import com.example.amethyst.service.QuestionService;

@RestController
public class QuestionController {

	@Autowired
	QuestionService questionService;
	
	@GetMapping("/questions")
	public List<Question> getAllQuestions(){
		return questionService.findAll();		
	}
	
	@GetMapping("questions/{id}")
	public Question getQuestionById(@PathVariable(value="id") Long id) {
		return questionService.getQuestionById(id);
	}
	
	@PutMapping("/questions/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Question updateQuestion(@PathVariable("id") Long id,
			@RequestBody Question question) {
		questionService.getQuestionById(id);
		return questionService.saveQuestion(question);				
	}
	
	@DeleteMapping("questions/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteQuestion(@PathVariable("id") Long id) {
		questionService.deleteQuestionById(id);
	}
}
