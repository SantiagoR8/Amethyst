package com.example.amethyst;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.example.amethyst.model.Evaluation;
import com.example.amethyst.model.Question;
import com.example.amethyst.repository.EvaluationRepository;
import com.example.amethyst.service.EvaluationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EvaluationIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	EvaluationRepository evaluationRepository;

	@Test
	public void createEvaluationtest() throws Exception {
		Evaluation evaluation = new Evaluation();
		evaluation.setName("Prueba");
		evaluation.setQuestions(new HashSet<Question>());
		evaluation.setVersion(1);
		
		MvcResult mvcResult = mockMvc.perform(post("/evaluations/")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(evaluation)))
		.andExpect(status().isCreated()).andReturn();
		
		evaluation = objectMapper.readValue( 
				mvcResult.getResponse().getContentAsString(),
				Evaluation.class);
		
		Evaluation eval = evaluationRepository
				.findById(evaluation.getId()).orElse(null);
		assertThat(eval.getName()).isEqualTo(evaluation.getName());
	}
	
	@Test
	public void updateEvaluationtest() throws Exception {
		Evaluation evaluation = new Evaluation();
		evaluation.setName("Prueba");
		evaluation.setQuestions(new HashSet<Question>());
		evaluation.setVersion(1);
		
		evaluation = evaluationRepository.save(evaluation);
		evaluation.setName("Prueba2");
		
		mockMvc.perform(put("/evaluations/{id}", evaluation.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(evaluation)))
		.andExpect(status().isOk());
		
		Evaluation eval = evaluationRepository
				.findById(evaluation.getId()).orElse(null);
		assertThat(eval.getName()).isEqualTo(evaluation.getName());
	}
	
	@Test
	public void getEvaluationtest() throws Exception {
		Evaluation evaluation = new Evaluation();
		evaluation.setName("Prueba");
		evaluation.setQuestions(new HashSet<Question>());
		evaluation.setVersion(1);
		
		evaluation = evaluationRepository.save(evaluation);
		
		mockMvc.perform(get("/evaluations/{id}", evaluation.getId()))
			.andExpect(status().isOk());
		
		Evaluation eval = evaluationRepository
				.findById(evaluation.getId()).orElse(null);
		assertThat(eval.getVersion()).isEqualTo(evaluation.getVersion());
	}

}
