package com.example.amethyst.service;

import java.util.List;

import com.example.amethyst.model.Question;

public interface QuestionService {

	List<Question> findAll();
	Question getQuestionById(Long id);
	Question saveQuestion(Question question);
	void deleteQuestion(Question question);
}
