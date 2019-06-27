package com.teamLong.java401d.midterm.troublemaker;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import com.teamLong.java401d.midterm.troublemaker.controller.UserAccountController;
import com.teamLong.java401d.midterm.troublemaker.model.RoleType;
import com.teamLong.java401d.midterm.troublemaker.model.Severity;
import com.teamLong.java401d.midterm.troublemaker.model.Ticket;
import com.teamLong.java401d.midterm.troublemaker.model.UserAccount;
import com.teamLong.java401d.midterm.troublemaker.repository.RoleRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.TicketRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
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
		private TicketRepository ticketRepository;

		@Autowired
		private UserRepository userRepository;

		@Autowired
		private RoleRepository roleRepository;

		@Autowired
		private MockMvc mvc;

		@Before
		public void setup(){
			mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		}

		public static RequestPostProcessor testUser(){
			return user("user").password("pass").authorities(new GrantedAuthority() {
				@Override
				public String getAuthority() {
					return "role_admin";
				}
			});
		}

		@WithMockUser
		@Test
		public void testHomePage() throws Exception {
			mvc.perform(get("/").with(testUser())).andExpect(content().string(containsString("")));
		}

		@WithMockUser
		@Test
		public void testIntegrationAddAccount() throws Exception {
			mvc.perform(get("/login").with(testUser())).andExpect(content().string(containsString("Ticket Management")));
		}

		@WithMockUser
		@Test
		public void logoutTest() throws Exception {
			mvc.perform(get("/login").with(testUser())).andExpect(content().string(containsString("Ticket Management")));
			mvc.perform(get("/logout").with(testUser())).andExpect(content().string(containsString("")));
		}

		@WithMockUser
		@Test
		public void testCreateTicketPage() throws Exception {
			mvc.perform(get("/ticket").with(testUser())).andExpect(content().string(containsString("Create a Ticket")));
		}

		@WithMockUser
		@Test
		public void testAllTicketPage() throws Exception {
			mvc.perform(get("/tickets/all").with(testUser())).andExpect(content().string(containsString("All Tickets")));
		}

		@WithMockUser
		@Test
		public void testEditTicketPage() throws Exception {
			Ticket ticket = ticketRepository.findByTitle("initial ticker");
			String url = "/edit/"+ticket.getId();
			mvc.perform(get(url).with(testUser())).andExpect(content().string(containsString("Edit your ticket")));
		}

		@Test
		public void testUserAccount(){
			UserAccount newUser = new UserAccount();
			newUser.setFirstName("Anthony");
			newUser.setLastName("Berry");
			newUser.setUsername("antberry");
			newUser.setPassword("Password1234#");
			newUser.setConfirmPassword("Password1234#");

			assertEquals("Anthony",newUser.getFirstName());
			assertEquals("Berry", newUser.getLastName());
			assertEquals("antberry", newUser.getUsername());
			assertEquals("Password1234#", newUser.getPassword());
		}

		@Test
		public void testTicketCreation(){
			Ticket newTicket = new Ticket();
			newTicket.setTitle("Computer Broke");
			newTicket.setTicketLvl(Severity.LOW);
			newTicket.setSummary("help me!");

			assertEquals(Severity.LOW, newTicket.getTicketLvl());
			assertEquals("Computer Broke", newTicket.getTitle());
			assertEquals("help me!", newTicket.getSummary());
		}

		@Test
		public void testRoleType(){
			RoleType User = new RoleType();
			User.setRole("user");

			assertEquals("user", User.getRole());


		}
	}
}
