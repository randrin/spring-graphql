package com.fullstack.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullstack.api.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringGraphqlApplicationTests {

	private static final String URI_ADD_PERSONS = "/person/addPersons";
	private static final String URI_GET_PERSONS = "/person/getPersons";

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	ObjectMapper objectMapper = new ObjectMapper();

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void addPersons() throws Exception {
		List<Person> personList = new ArrayList<>();
		Person person = new Person();
		person.setEmail("nzeukangrandrin@gmail.com");
		person.setMobile("3296187465");
		person.setName("Nzeukang Nimpa Randrin");
		String [] address = {"Via Santa Maria", "31/7 Vigevano", "27029 PV"};
		person.setAddress(address);
		personList.add(person);

		String jsonRequest = objectMapper.writeValueAsString(personList);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URI_ADD_PERSONS).content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andReturn();
	}
}
