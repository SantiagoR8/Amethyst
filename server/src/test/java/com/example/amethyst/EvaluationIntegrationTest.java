package com.example.amethyst;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
	public void createEvaluationTest() throws Exception {
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
	public void createEvaluationWithQuestionsTest() throws Exception {
		Question question = new Question();
		question.setTitle("Pregunta 1");
		question.setBody("Lorem Ipsum");
		
		Evaluation evaluation = new Evaluation();
		evaluation.setName("Prueba");
		evaluation.addQuestion(question);
		
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
	public void createEvaluationWithDuplicateQuestionTest() throws Exception {
		Question question = new Question();
		question.setTitle("Pregunta 1");
		question.setBody("Lorem Ipsum");
		
		Evaluation evaluation = new Evaluation();
		evaluation.setName("Prueba");
		evaluation.addQuestion(question);
		evaluation.addQuestion(question);
				
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
		// HashSet ensures there are no duplicate items
		assertThat(eval.getQuestions().size()).isEqualTo(evaluation.getQuestions().size());
	}
	
	@Test
	public void createEvaluationsWithQuestionsTest() throws Exception {
		Question questionOne = new Question();
		questionOne.setTitle("Pregunta 1");
		questionOne.setBody("Lorem Ipsum");
		Question questionTwo = new Question();
		questionTwo.setTitle("Pregunta 2");
		questionTwo.setBody("Lorem Ipsum");
		
		Evaluation firstEval = new Evaluation();
		firstEval.setName("Prueba 1");
		firstEval.addQuestion(questionOne);
		firstEval.addQuestion(questionTwo);
		
		Evaluation secondEval = new Evaluation();
		secondEval.setName("Prueba 1");
		secondEval.addQuestion(questionOne);
		
		MvcResult mvcResult = mockMvc.perform(post("/evaluations/")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(firstEval)))
		.andExpect(status().isCreated()).andReturn();
		firstEval = objectMapper.readValue( 
				mvcResult.getResponse().getContentAsString(),
				Evaluation.class);
		
		mvcResult = mockMvc.perform(post("/evaluations/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(secondEval)))
			.andExpect(status().isCreated()).andReturn();
		secondEval = objectMapper.readValue( 
					mvcResult.getResponse().getContentAsString(),
					Evaluation.class);	

		Evaluation evalOne = evaluationRepository
				.findById(firstEval.getId()).orElse(null);
		Evaluation evalTwo = evaluationRepository
				.findById(secondEval.getId()).orElse(null);
		assertThat(evalOne.getName()).isEqualTo(firstEval.getName());
		assertThat(evalOne.getQuestions().size()).isEqualTo(firstEval.getQuestions().size());
		assertThat(evalTwo.getName()).isEqualTo(secondEval.getName());
		assertThat(evalTwo.getQuestions().size()).isEqualTo(secondEval.getQuestions().size());
	}
	
	@Test
	public void updateEvaluationTest() throws Exception {
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
	public void getEvaluationByIdTest() throws Exception {
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
	
	@Test
	public void getAllEvaluationsTest() throws Exception {
		Evaluation evaluation = new Evaluation();
		evaluation.setName("Prueba");
		evaluation.setQuestions(new HashSet<Question>());
		evaluation.setVersion(1);
		
		evaluation = evaluationRepository.save(evaluation);
		
		MvcResult mvcResult = mockMvc.perform(get("/evaluations/"))
				.andExpect(status().isOk()).andReturn();
		Evaluation[] result = objectMapper.readValue(
				mvcResult.getResponse().getContentAsString(),
				Evaluation[].class);	
		
		List<Evaluation> evals = Arrays.asList(result);
		List<Evaluation> evaluations = evaluationRepository.findAll();
		
		assertThat(evals.size()).isEqualTo(evaluations.size());
	}
	
	@Test
	public void deleteEvaluationTest() throws Exception {
		Evaluation evaluation = new Evaluation();
		evaluation.setName("Prueba");
		evaluation.setQuestions(new HashSet<Question>());
		evaluation.setVersion(1);
		
		evaluation = evaluationRepository.save(evaluation);
		
		MvcResult mvcResult = mockMvc
				.perform(delete("/evaluations/{id}", evaluation.getId()))
				.andExpect(status().isOk()).andReturn();	
			
		assertThat(mvcResult.getResponse().getContentAsString()
				.contains("Could not found test " + evaluation.getId() ));
	}

}
