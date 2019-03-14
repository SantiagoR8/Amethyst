package com.example.amethyst;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.amethyst.model.Evaluation;
import com.example.amethyst.model.Option;
import com.example.amethyst.model.Question;
import com.example.amethyst.repository.QuestionRepository;
import com.example.amethyst.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuestionIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	QuestionRepository questionRepository;

	@Test
	public void createQuestionWithNoOptionsTest() throws Exception {
		Question question = new Question();
		question.setTitle("Pregunta 1");
		question.setBody("Lorem Ipsum...");
		
		mockMvc.perform(post("/questions/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(question)))
			.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void createQuestionWithOptionsTest() throws Exception {
		Question question = new Question();
		question.setTitle("Pregunta 1");
		question.setBody("Lorem Ipsum...");
		
		Option optionOne = new Option();
		optionOne.setText("Opción 1");
		optionOne.setCorrect(true);
		optionOne.setQuestion(question);
		
		Set<Option> options = new HashSet<>();
		options.add(optionOne);
		
		question.setOptions(options);
		
		MvcResult mvcResult = mockMvc.perform(post("/questions/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(question)))
			.andExpect(status().isCreated()).andReturn();
		
		question = objectMapper.readValue( 
				mvcResult.getResponse().getContentAsString(),
				Question.class);
		
		Question questionAssert = questionRepository
				.findById(question.getId()).orElse(null);
		assertThat(questionAssert.getTitle()).isEqualTo(question.getTitle());
	}

}
