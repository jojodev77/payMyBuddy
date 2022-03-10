package pmb.pmb.controllerTest;

import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pmb.pmb.controller.UserController;
import pmb.pmb.dto.LocalUser;
import pmb.pmb.dto.UserReferenceTransaction;
import pmb.pmb.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	private MockMvc mockMvc;
	
	@InjectMocks
	private static UserController userController;
	
	@Mock
	UserService userService;
	

	@BeforeEach
	private void setUpPerTest() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	/**
	 * Description test controller of information of social login
	 * @throws Exception
	 */
	@Test
	public void getCurrentUser()  {
		//GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
			LocalUser localUser = null;
			User.withUsername("Admin");
			ObjectMapper objectMapper = new ObjectMapper();
			List<UserReferenceTransaction> list = new ArrayList<>();
		//WHEN
			lenient().when(userService.listReferenceTransaction()).thenReturn(list);
		//THEN
		try {
			mockMvc.perform(get("/api/user/me").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8).content(objectMapper.writeValueAsString(localUser))).andDo(print()).andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description get this list of userReferenceAccount with succes
	 * @throws Exception
	 */
	@Test
	public void getListUserReferenceTransaction() {
		//GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		//WHEN
		//THEN
		try {
			mockMvc.perform(get("/api/user/listUserReferenceTransaction").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description get tcurrent information role user  with succes
	 * @throws Exception
	 */
	@Test
	public void getCurrentUserTest() {
		//GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		//WHEN
		//THEN
		try {
			mockMvc.perform(get("/api/user").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description get tcurrent information role user  with succes
	 * @throws Exception
	 */
	@Test
	public void getUserInformationsTest() {
		//GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		ObjectMapper objectMapper = new ObjectMapper();
		String email = "ff@fff.com";
		//WHEN
		//THEN
		try {
			mockMvc.perform(post("/api/user/getUserInformations").accept(MediaType.APPLICATION_JSON)
					.contentType(MEDIA_TYPE_JSON_UTF8).content(objectMapper.writeValueAsString(email)))
					.andDo(print()).andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @Description get tcurrent information role user  with succes
	 * @throws Exception
	 */
	@Test
	public void getCurrentUserMeTest() {
		//GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		//WHEN
		//THEN
		try {
			mockMvc.perform(get("/api//user/me").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)).andDo(print()).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
