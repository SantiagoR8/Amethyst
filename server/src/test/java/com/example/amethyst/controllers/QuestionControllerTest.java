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
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.amethyst.model.Question;
import com.example.amethyst.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {
		
	private static final String BASE_URL = "/questions/";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private QuestionService questionService;

	@Test
	public void getAllQuestions_NoneTest() throws Exception {
		
		mockMvc
			.perform(get(BASE_URL).accept(MediaType.APPLICATION_JSON))				
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.length()").value(0))
			.andReturn();
		
		verify(questionService, times(1)).findAll();
	}
	
	@Test
	public void getAllQuestions_atLeastOneTest() throws Exception {
		Question question = new Question();
		String questionContent = mapper.writeValueAsString(question);
		List<Question> questions = new ArrayList<>(Arrays.asList(question));
		Mockito.when(questionService.findAll()).thenReturn(questions);
		MvcResult mvcResult = mockMvc
				.perform(get(BASE_URL).accept(MediaType.APPLICATION_JSON))				
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(String.format("[%s]", questionContent)))
				.andExpect(jsonPath("$.length()").value(1))				
				.andReturn();
		
		verify(questionService, times(1)).findAll();
	}
	
	@Test
	public void testGetQuestionById() throws Exception {
		Question question = new Question();
		String questionContent = mapper.writeValueAsString(question);
		Mockito.when(questionService.getQuestionById(1L)).thenReturn(question);
		MvcResult mvcResult = mockMvc
				.perform(get(BASE_URL + 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(questionContent))
				.andReturn();
	}
	
	@Test
	public void addQuestionTest() throws Exception {
		Question question = new Question();
		String questionContent = mapper.writeValueAsString(question);
		Mockito.when(questionService.saveQuestion(ArgumentMatchers.any(Question.class))).thenReturn(question);
		MvcResult mvcResult = mockMvc.perform(post(BASE_URL)
					.content(questionContent)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isMethodNotAllowed())
				.andReturn();
		
		verify(questionService, times(0)).saveQuestion(question);
	}
	
	@Test
	public void deleteQuestionTest() throws Exception {		
		Question question = new Question();
		mockMvc.perform(delete(BASE_URL + 1))
				.andExpect(status().isOk());	
		verify(questionService, times(1)).deleteQuestionById(1L);
	}

}

