package com.example.amethyst.repository.exception;

public class EvaluationNotFoundException extends RuntimeException {

	public EvaluationNotFoundException(Long id) {
		super("Could not found test " + id);
	}
}
