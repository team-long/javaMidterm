package com.teamLong.java401d.midterm.troublemaker;

import com.teamLong.java401d.midterm.troublemaker.controller.UserAccountController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


public class TroublemakerApplicationTests {



	@RunWith(SpringRunner.class)
	@SpringBootTest
	@AutoConfigureMockMvc
	public class UserControllerTest {
		@Autowired
		UserAccountController userAccountController;

		@Autowired
		MockMvc mockMvc;

		@Test
		public void contextLoads(){}

		@Test
		public void testControllerIsAutowired(){
			assertNotNull(userAccountController);
		}

		@Test
		public void testRequestToIndexPage() throws Exception{
			mockMvc.perform(get("/")).andExpect(content().string(containsString("Welcome to TroubleMaker")));
		}

		@Test
		public void testRequestToLoginPage() throws Exception{
			mockMvc.perform(get("/login")).andExpect(content().string(containsString("Login")));
		}

		@Test
		public void testRequestToSignUpPage() throws Exception{
			mockMvc.perform(get("/signup")).andExpect(content().string(containsString("Sign Up")));
		}
	}
}
