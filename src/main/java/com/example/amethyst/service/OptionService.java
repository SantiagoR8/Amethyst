package com.example.amethyst.service;

import java.util.List;

import com.example.amethyst.model.Option;

public interface OptionService {

	List<Option> findAll();
	Option getOptionById(Long id);
	Option saveOption(Option option);
	void deleteOption(Option option);
}
