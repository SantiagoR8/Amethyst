package com.example.amethyst.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.amethyst.model.Option;
import com.example.amethyst.repository.OptionRepository;

@Service
public class OptionServiceImpl implements OptionService {

	@Autowired
	OptionRepository optionRepository;
	
	@Override
	public List<Option> findAll() {
		return optionRepository.findAll();
	}

	@Override
	public Option getOptionById(Long id) {
		return optionRepository.getOne(id);
	}

	@Override
	public Option saveOption(Option option) {
		return optionRepository.save(option);
	}

	@Override
	public void deleteOption(Option option) {
		optionRepository.delete(option);
	}
	
}
