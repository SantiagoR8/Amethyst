package com.example.amethyst.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.amethyst.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
