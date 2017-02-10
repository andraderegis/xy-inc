package com.zup.xyapi.controller;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zup.xyapi.conf.AppWebConfig;
import com.zup.xyapi.conf.DevelopmentHibernateConf;
import com.zup.xyapi.conf.ProductionHibernateConf;
import com.zup.xyapi.conf.TestHibernateConf;
import com.zup.xyapi.model.InterestPoint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppWebConfig.class, ProductionHibernateConf.class, DevelopmentHibernateConf.class,
		TestHibernateConf.class })
@WebAppConfiguration
@ActiveProfiles("test")
public class InterestPointControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	private MockHttpServletRequestBuilder mockBuilder;

	@Before
	public void setUp() {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(context);
		this.mockMvc = builder.build();
	}

	@Test
	public void shouldBePointsSuccessRequest() {
		try {

			this.mockBuilder = MockMvcRequestBuilders.get("/points");

			this.mockMvc.perform(this.mockBuilder).andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void shouldBePointsByProximitySuccessRequest() {
		try {

			this.mockBuilder = MockMvcRequestBuilders.get("/points?lat=20&lng=10&distance=10");

			this.mockMvc.perform(this.mockBuilder).andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	@Ignore
	public void shouldBeInvalidRequestToSavePoint() {
		try {

			ObjectMapper mapper = new ObjectMapper();
			InterestPoint model = new InterestPoint(null, 0, 10);

			String json = mapper.writeValueAsString(model);

			this.mockBuilder = MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(json);

			this.mockMvc.perform(this.mockBuilder).andExpect(MockMvcResultMatchers.status().isBadRequest())
					.andDo(MockMvcResultHandlers.print());

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void shouldBeSuccessRequestToSavePoint(){
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			InterestPoint model = new InterestPoint("Escola", 10, 10);

			String json = mapper.writeValueAsString(model);

			this.mockBuilder = MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(json);

			this.mockMvc.perform(this.mockBuilder).andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print());
			
		} catch (Exception e){
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
