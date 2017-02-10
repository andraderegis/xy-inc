package com.zup.xyapi.controller;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
import com.zup.xyapi.response.ApiResponse;

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

	private ApiResponse<InterestPoint> jsonObj;
	
	private ObjectMapper objMapper;


	@Before
	public void setUp() {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(context);
		this.mockMvc = builder.build();
		
		objMapper = new ObjectMapper();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldBePointsSuccessRequest() {
		try {

			String expectedJson = "{\"code\": 200, \"message\": null, \"status\": \"OK\", \"data\": [{\"id\": 1, \"name\": \"Lanchonete\", \"coordinateX\": 27, \"coordinateY\": 12 }, {\"id\": 2, \"name\": \"Posto\", \"coordinateX\": 31, \"coordinateY\": 18 }, {\"id\": 3, \"name\": \"Joalheria\", \"coordinateX\": 15, \"coordinateY\": 12 }, {\"id\": 4, \"name\": \"Floricultura\", \"coordinateX\": 19, \"coordinateY\": 21 }, {\"id\": 5, \"name\": \"Pub\", \"coordinateX\": 12, \"coordinateY\": 8 }, {\"id\": 6, \"name\": \"Supermercado\", \"coordinateX\": 23, \"coordinateY\": 6 }, {\"id\": 7, \"name\": \"Churrascaria\", \"coordinateX\": 28, \"coordinateY\": 2 }, {\"id\": 8, \"name\": \"Escola\", \"coordinateX\": 10, \"coordinateY\": 10 } ] }";
			
			this.jsonObj = this.objMapper.readValue(expectedJson, ApiResponse.class);
			this.mockBuilder = MockMvcRequestBuilders.get("/points");

			MvcResult result = this.mockMvc.perform(this.mockBuilder)
					.andExpect(MockMvcResultMatchers.request().asyncStarted())
					.andExpect(MockMvcResultMatchers.request().asyncResult(instanceOf(ResponseEntity.class)))
					.andReturn();

			this.mockMvc.perform(asyncDispatch(result)).andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(content().json(this.objMapper.writeValueAsString(this.jsonObj))).andDo(MockMvcResultHandlers.print());

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldBePointsByProximitySuccessRequest() {
		try {

			String expectedJson = "{\"code\":200,\"message\":null,\"status\":\"OK\",\"data\":[{\"id\":1,\"name\":\"Lanchonete\",\"coordinateX\":27,\"coordinateY\":12},{\"id\":3,\"name\":\"Joalheria\",\"coordinateX\":15,\"coordinateY\":12},{\"id\":5,\"name\":\"Pub\",\"coordinateX\":12,\"coordinateY\":8},{\"id\":6,\"name\":\"Supermercado\",\"coordinateX\":23,\"coordinateY\":6}]}";

			this.jsonObj = this.objMapper.readValue(expectedJson, ApiResponse.class);
			this.mockBuilder = MockMvcRequestBuilders.get("/points?lat=20&lng=10&distance=10");

			MvcResult result = this.mockMvc.perform(this.mockBuilder)
					.andExpect(MockMvcResultMatchers.request().asyncStarted())
					.andExpect(MockMvcResultMatchers.request().asyncResult(instanceOf(ResponseEntity.class)))
					.andReturn();

			this.mockMvc.perform(asyncDispatch(result)).andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(content().json(this.objMapper.writeValueAsString(this.jsonObj))).andDo(MockMvcResultHandlers.print());

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void shouldBeInvalidModelRequestToSavePoint() {
		try {

			ObjectMapper mapper = new ObjectMapper();
			InterestPoint model = new InterestPoint(null, 0, 10);

			String json = mapper.writeValueAsString(model);
			

			this.mockBuilder = MockMvcRequestBuilders.post("/points")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(json);
			
			this.mockMvc.perform(this.mockBuilder).andExpect(MockMvcResultMatchers.status().isBadRequest())
					.andDo(MockMvcResultHandlers.print());

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void shouldBeInvalidContentTypeRequestToSavePoint() {
		try {

			ObjectMapper mapper = new ObjectMapper();
			InterestPoint model = new InterestPoint("Escola", 10, 10);

			String json = mapper.writeValueAsString(model);

			this.mockBuilder = MockMvcRequestBuilders.post("/points").content(json);
			
			this.mockMvc.perform(this.mockBuilder).andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
					.andDo(MockMvcResultHandlers.print());

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void shouldBeSuccessRequestToSavePoint() {
		try {

			ObjectMapper mapper = new ObjectMapper();
			InterestPoint model = new InterestPoint("Escola", 10, 10);

			String json = mapper.writeValueAsString(model);

			this.mockBuilder = MockMvcRequestBuilders.post("/points").contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(json);
			
			MvcResult mvcResult = this.mockMvc.perform(this.mockBuilder)
					.andExpect(MockMvcResultMatchers.request().asyncStarted())
					.andExpect(MockMvcResultMatchers.request().asyncResult(instanceOf(ResponseEntity.class)))
					.andReturn();

			this.mockMvc.perform(asyncDispatch(mvcResult)).andExpect(MockMvcResultMatchers.status().isOk())
					.andDo(MockMvcResultHandlers.print());

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
