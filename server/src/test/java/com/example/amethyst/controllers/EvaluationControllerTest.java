package com.example.amethyst.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.example.amethyst.model.Evaluation;
import com.example.amethyst.repository.EvaluationRepository;
import com.example.amethyst.service.EvaluationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(EvaluationController.class)
public class EvaluationControllerTest {
		
	private static final String BASE_URL = "/evaluations/";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private EvaluationService evaluationService;

	@Test
	public void getAllEvaluations_NoneTest() throws Exception {
		
		MvcResult mvcResult = mockMvc
				.perform(get(BASE_URL).accept(MediaType.APPLICATION_JSON))				
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.length()").value(0))
				.andReturn();
		
		verify(evaluationService, times(1)).findAll();
	}
	
	@Test
	public void getAllEvaluations_atLeastOneTest() throws Exception {
		Evaluation evaluation = new Evaluation();
		evaluation.setName("Prueba");
		String evaluationContent = mapper.writeValueAsString(evaluation);
		List<Evaluation> evaluations = new ArrayList<>(Arrays.asList(evaluation));
		Mockito.when(evaluationService.findAll()).thenReturn(evaluations);
		MvcResult mvcResult = mockMvc
				.perform(get(BASE_URL).accept(MediaType.APPLICATION_JSON))				
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(String.format("[%s]", evaluationContent)))
				.andExpect(jsonPath("$.length()").value(1))				
				.andReturn();
		
		verify(evaluationService, times(1)).findAll();
	}
	
	@Test
	public void testGetEvaluationById() throws Exception {
		Evaluation evaluation = new Evaluation();
		evaluation.setName("Prueba");
		String evaluationContent = mapper.writeValueAsString(evaluation);
		Mockito.when(evaluationService.getEvaluationById(1L)).thenReturn(evaluation);
		MvcResult mvcResult = mockMvc
				.perform(get(BASE_URL + 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(evaluationContent))
				.andReturn();
	}
	
	@Test
	public void addEvaluationTest() throws Exception {
		Evaluation evaluation = new Evaluation("Prueba");
		String evaluationContent = mapper.writeValueAsString(evaluation);
		Mockito.when(evaluationService.saveEvaluation(ArgumentMatchers.any(Evaluation.class))).thenReturn(evaluation);
		MvcResult mvcResult = mockMvc.perform(post(BASE_URL)
					.content(evaluationContent)
					.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(evaluationContent))
				.andReturn();
		
		verify(evaluationService, times(1)).saveEvaluation(evaluation);
	}
	
	@Test
	public void deleteEvaluationTest() throws Exception {		
		Evaluation evaluation = new Evaluation();
		evaluation.setName("Prueba");
		mockMvc.perform(delete(BASE_URL + 1))
				.andExpect(status().isOk());	
		verify(evaluationService, times(1)).deleteEvaluationById(1L);
	}

}

