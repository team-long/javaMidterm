package com.teamLong.java401d.midterm.troublemaker;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import com.teamLong.java401d.midterm.troublemaker.controller.UserAccountController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


public class TroublemakerApplicationTests {

	@RunWith(SpringRunner.class)
	@SpringBootTest
	@AutoConfigureMockMvc
	public static class UserControllerTest {
		@Autowired
		UserAccountController userAccountController;

		@Autowired
		private WebApplicationContext context;

		@Autowired
		private MockMvc mvc;

		@Before
		public void setup(){
			mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		}

		public static RequestPostProcessor testUser(){
			return user("user").password("pass").roles("ADMIN");
		}

		@WithMockUser
		@Test
		public void testIntegrationAddAccount() throws Exception {
			mvc.perform(get("/login").with(testUser())).andExpect(content().string(containsString("Ticket Management")));
		}

		@WithMockUser
		@Test
		public void testAllTicketPage() throws Exception {
			mvc.perform(get("/tickets/all").with(testUser())).andExpect(content().string(containsString("Ticket Management")));
		}

//		@Test
//		public void contextLoads(){}
//
//		@Test
//		public void testControllerIsAutowired(){
//			assertNotNull(userAccountController);
//		}

//		@Test
//		public void testRequestToIndexPage() throws Exception{
//			mvc.perform(get("/")).andExpect(content().string(containsString("Welcome to TroubleMaker")));
//		}
//
//		@Test
//		public void testRequestToLoginPage() throws Exception{
//			mvc.perform(get("/login")).andExpect(content().string(containsString("Login")));
//		}
//
//		@Test
//		public void testRequestToSignUpPage() throws Exception{
//			mvc.perform(get("/signup")).andExpect(content().string(containsString("Sign Up")));
//		}
	}
}
