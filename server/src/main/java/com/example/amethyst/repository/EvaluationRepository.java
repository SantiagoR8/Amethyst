package com.example.amethyst.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.amethyst.model.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

}
