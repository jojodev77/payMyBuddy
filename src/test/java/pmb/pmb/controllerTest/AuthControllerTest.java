package pmb.pmb.controllerTest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pmb.pmb.controller.AuthController;
import pmb.pmb.controller.UserController;
import pmb.pmb.dto.LocalUser;
import pmb.pmb.dto.LoginRequest;
import pmb.pmb.dto.SignUpRequest;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.View;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
	private MockMvc mockMvc;

	@InjectMocks
	private static AuthController authController;

	@Autowired
	AuthenticationManager authenticationManager;

	@BeforeEach
	private void setUpPerTest() {
		mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
	}

	/**
	 * @throws Exception
	 * @throws JsonProcessingException
	 * @Description test call http siginin with succes
	 */
	@Test
	public void signinControllerSuccesTest() throws JsonProcessingException, Exception {
		// GIVEN
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("admin@jojo");
		loginRequest.setPassword("admin@");
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		mockMvc.perform(post("/api/auth/signin").accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest))).andDo(print()).andExpect(status().isOk());
	}
	
	/**
	 * @throws Exception
	 * @throws JsonProcessingException
	 * @Description test call http siginin with error
	 */
	@Test
	public void signinControllerErrorTest() throws JsonProcessingException, Exception {
		// GIVEN
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(null);
		loginRequest.setPassword(null);
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		mockMvc.perform(post("/api/auth/signin").accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest))).andDo(print()).andExpect(status().isBadRequest());
	}
	
	
	/**
	 * @throws Exception
	 * @throws JsonProcessingException
	 * @Description test call http signup with succes
	 */
	@Test
	public void signupControllerSuccesTest() throws JsonProcessingException, Exception {
		// GIVEN
		SignUpRequest signUpRequest = new SignUpRequest(null, "admintest", "admintest@test", "adminadmin", null);
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		mockMvc.perform(post("/api/auth/signin").accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signUpRequest))).andDo(print()).andExpect(status().isOk());
	}
	
	
	/**
	 * @throws Exception
	 * @throws JsonProcessingException
	 * @Description test call http signup with succes
	 */
	@Test
	public void signupControllerSuccesError() throws JsonProcessingException, Exception {
		// GIVEN
		LoginRequest loginRequest = new LoginRequest();
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		mockMvc.perform(post("/api/auth/signin").accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest))).andDo(print()).andExpect(status().isBadRequest());
	}
	
	
}
