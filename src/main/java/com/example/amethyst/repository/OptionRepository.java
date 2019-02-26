package com.example.amethyst.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.amethyst.model.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {

}
