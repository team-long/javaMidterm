package com.teamLong.java401d.midterm.troublemaker;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import com.teamLong.java401d.midterm.troublemaker.controller.UserAccountController;
import com.teamLong.java401d.midterm.troublemaker.email.EmailSender;
import com.teamLong.java401d.midterm.troublemaker.model.*;
import com.teamLong.java401d.midterm.troublemaker.repository.RoleRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.TicketRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.UpdateRepository;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

///Not all models were tested.
//should get rid of testuser, not necessary

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
		private UpdateRepository updateRepository;

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

		public static RequestPostProcessor nonAdminUser(){
			return user("user").password("pass");
		}

		public static RequestPostProcessor adminUser(){
			return user("troublemakeramazon@gmail.com").password("admin");
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
		public void testGetCreateTicketPage() throws Exception {
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
			Ticket ticket = ticketRepository.findByTitle("Example Ticket: Test");
			String url = "/edit/"+ticket.getId();
			mvc.perform(get(url).with(testUser())).andExpect(content().string(containsString("Edit your ticket")));
		}

		@WithMockUser
		@Test
		public void testAboutUSPage() throws Exception {
			String url = "/aboutus/";
			mvc.perform(get(url).with(adminUser())).andExpect(content().string(containsString("About Us")));
		}

		@WithMockUser
		@Test
		public void testProfilePage() throws Exception {
			String url = "/profile";
			mvc.perform(get(url).with(adminUser())).andExpect(content().string(containsString("Profile")));
		}

		@WithMockUser
		@Test
		public void testAllTicketNonAdmin() throws Exception {
			String url = "/tickets/all";
			mvc.perform(get(url).with(nonAdminUser())).andExpect(content().string(containsString("")));
		}

		//End to End Creation

		//Create a ticket with nothing
		@WithMockUser
		@Test(expected = org.springframework.web.util.NestedServletException.class)
		public void testCreateTicketwithNull() throws Exception {
			mvc.perform(post("/create/ticket").with(testUser())).andDo(print())
					.andExpect(status().isOk());
		}

		//this tests creation of the ticket
		//and be able check for it
		@WithMockUser
		@Test(expected = org.springframework.web.util.NestedServletException.class)
		public void testCreateTicketPost() throws Exception {
			String title = "Create Ticket";
			mvc.perform(post("/create/ticket")
					.param("title", title)
					.param("ticketLvl", "LOW")
					.param("summary", "hey its me")
					.with(testUser())).andDo(print())
					.andExpect(status().isOk());

			Ticket ticket = ticketRepository.findByTitle(title);
			String url = "/edit/"+ticket.getId();
			mvc.perform(get(url).with(testUser())).andExpect(content().string(containsString("Edit your ticket")));

		}


		//this tests creation of the user
		//and be able check for it
		@WithMockUser
		@Test(expected = org.springframework.web.util.NestedServletException.class)
		public void testCreateUserPost() throws Exception {

			mvc.perform(post("/create/ticket")
					.param("username", "test@codefellows.com")
					.param("password", "Name23!!")
					.param("confirmPassword", "Name23!!")
					.param("firstName", "Name")
					.with(testUser())).andDo(print())
					.andExpect(status().isOk());

			UserAccount user = userRepository.findByUsername("test@codefellows.com");
			assertEquals("Name", user.getFirstName());

		}


		//this tests creation of the comment
		//and be able check for it
		@WithMockUser
		@Test()
		public void testCreateComments() throws Exception {

			Ticket ticket = ticketRepository.findByTitle("Example Ticket: Test");

			String longId = ""+ticket.getId();

			mvc.perform(post("/tickets/comment")
					.param("ticketId", longId)
					.param("update", "Test Comment")
					.with(testUser())).andDo(print())
					.andExpect(status().isFound());

			List<String> comments = new ArrayList<>();
			List<Update> updatedTicketComments = updateRepository.findAllByTicketId(ticket.getId());
			updatedTicketComments.forEach(update -> comments.add(update.getComments()));
			assertTrue(comments.contains("Test Comment"));

		}

		//Test email sender
		@Test
		public void testsendEmailPass() {
			 boolean status = EmailSender.sendEmail(userRepository.findByUsername("troublemakeramazon@gmail.com"), null, "INTRO", null);
			 assertTrue(status);
		}


		@Test(expected=NullPointerException.class)
		public void testsendEmailFail() {
			boolean status = EmailSender.sendEmail(null, null, "INTRO", null);
		}






		//Unit Testing
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

		@Test
		public void testComment(){
			Update test = new Update();
			test.setComments("hey");
			assertEquals("hey", test.getComments());
		}

		@Test
		public void testCommentConstructor(){
			Ticket newTicket = new Ticket();
			newTicket.setTitle("Computer Broke");
			newTicket.setTicketLvl(Severity.LOW);
			newTicket.setSummary("help me!");

			Update test = new Update("hey", newTicket, "bobby");
			test.setComments("hey");
			assertEquals("hey", test.getComments());
			assertEquals(newTicket, test.getTicket());
		}
	}
}
