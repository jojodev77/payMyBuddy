package pmb.pmb.controllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.View;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;

import pmb.pmb.controller.UserController;
import pmb.pmb.dto.LocalUser;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	private MockMvc mockMvc;
	
	@InjectMocks
	private static UserController userController;
	

	@BeforeEach
	private void setUpPerTest() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	/**
	 * Description test controller of information of social login
	 * @throws Exception
	 */
	@Test
	public void getCurrentUser() throws Exception {
		//GIVEN
			LocalUser localUser = new LocalUser(null, null, false, false, false, false, null, null, null, null);
			User.withUsername("Admin");
			ObjectMapper objectMapper = new ObjectMapper();
		//WHEN
		//THEN
		mockMvc.perform(post("/api/user/me").accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(localUser))).andDo(print()).andExpect(status().isOk());
	}
	
	/**
	 * @Description get this list of userReferenceAccount with succes
	 * @throws Exception
	 */
	@Test
	public void getListUserReferenceTransaction() throws Exception {
		//GIVEN
		//WHEN
		//THEN
		mockMvc.perform(post("/api/user/listUserReferenceTransaction").accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
}
