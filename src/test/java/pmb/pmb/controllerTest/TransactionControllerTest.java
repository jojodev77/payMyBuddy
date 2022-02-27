package pmb.pmb.controllerTest;
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
import pmb.pmb.controller.TransactionController;
import pmb.pmb.dto.AddBuddy;
import pmb.pmb.dto.AddCash;
import pmb.pmb.dto.LoginRequest;
import pmb.pmb.dto.UserBuddy;

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

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
	private MockMvc mockMvc;
	
	@InjectMocks
 TransactionController transactionController;
	

	@BeforeEach
	private void setUpPerTest() {
		mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
	}
	
	/**
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 * @Description test controller for add a buddy with success
	 */
	@Test
	public void addBuddyForTransactionSuccesTest()  {
		// GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		AddBuddy addBuddy = new AddBuddy();
		addBuddy.setUserSetter("pmbaminminb");
		addBuddy.setUserGetter("pmbt@tttotb");
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		try {
			mockMvc.perform(post("/api/user/addBuddy").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)
					.content(objectMapper.writeValueAsString(addBuddy))).andDo(print()).andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 * @Description test controller for add a buddy with error
	 */
	@Test
	public void addBuddyForTransactionErrorTest() {
		// GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		AddBuddy addBuddy = new AddBuddy();
		addBuddy.setUserSetter(null);
		addBuddy.setUserGetter(null);
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		try {
			mockMvc.perform(post("/api/user/addBuddy").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)
					.content(objectMapper.writeValueAsString(addBuddy))).andDo(print()).andExpect(status().isBadRequest());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 * @Description test controller for get history transaction with success
	 */
	@Test
	public void getHistorySuccesTest()  {
		// GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		UserBuddy addBuddy = new UserBuddy();
		addBuddy.setUserSetter("pmbaminminb");
		addBuddy.setUserGetter("pmbt@tttotb");
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		try {
			mockMvc.perform(post("/api/user/history").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)
					.content(objectMapper.writeValueAsString(addBuddy))).andDo(print()).andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 * @Description test controller for get history transaction with error
	 */
	@Test
	public void getHistoryErrorTest()  {
		// GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		UserBuddy addBuddy = new UserBuddy();
		addBuddy.setUserSetter(null);
		addBuddy.setUserGetter(null);
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		try {
			mockMvc.perform(post("/api/user/history").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)
					.content(objectMapper.writeValueAsString(addBuddy))).andDo(print()).andExpect(status().isBadRequest());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 * @Description test controller for start to transaction with success
	 */
	@Test
	public void startTransactionSuccesTest()  {
		// GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		UserBuddy userBuddy = new UserBuddy();
		userBuddy.setUserSetter("pmbaminminb");
		userBuddy.setUserGetter("pmbt@tttotb");
		userBuddy.setAmount(200);
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		try {
			mockMvc.perform(post("/api/user/startTransaction").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)
					.content(objectMapper.writeValueAsString(userBuddy))).andDo(print()).andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 * @Description test controller for start to transaction with error
	 */
	@Test
	public void startTransactionErrorTest() {
		// GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		UserBuddy userBuddy = new UserBuddy();
		userBuddy.setUserSetter(null);
		userBuddy.setUserGetter(null);
		userBuddy.setAmount(200);
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		try {
			mockMvc.perform(post("/api/user/startTransaction").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)
					.content(objectMapper.writeValueAsString(userBuddy))).andDo(print()).andExpect(status().isBadRequest());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 * @Description test controller for get account situation with success
	 */
	@Test
	public void accountSituationSuccesTest() {
		// GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		UserBuddy userBuddy = new UserBuddy();
		userBuddy.setUserSetter("pmbaminminb");
		userBuddy.setUserGetter("pmbt@tttotb");
		userBuddy.setAmount(200);
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		try {
			mockMvc.perform(post("/api/user/accountSituation").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)
					.content(objectMapper.writeValueAsString(userBuddy))).andDo(print()).andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 * @Description test controller for get account situation with error
	 */
	@Test
	public void accountSituationErrorTest() {
		// GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		UserBuddy userBuddy = new UserBuddy();
		userBuddy.setUserSetter(null);
		userBuddy.setUserGetter(null);
		userBuddy.setAmount(200);
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		try {
			mockMvc.perform(post("/api/user/accountSituation").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)
					.content(objectMapper.writeValueAsString(userBuddy))).andDo(print()).andExpect(status().isBadRequest());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 * @Description test controller for get list of buddy with success
	 */
	@Test
	public void getListBuddySuccesTest()  {
		// GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		long id = 1;
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		try {
			mockMvc.perform(post("/api/user/getListBuddy").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)
					.content(objectMapper.writeValueAsString(id))).andDo(print()).andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 * @Description test controller for get list of buddy with error
	 */
	@Test
	public void getListBuddyErrorTest() {
		// GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		long id = (Long) null;
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		try {
			mockMvc.perform(post("/api/user/getListBuddy").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)
					.content(objectMapper.writeValueAsString(id))).andDo(print()).andExpect(status().isBadRequest());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 * @Description test controller for add cash with success
	 */
	@Test
	public void addCashSuccesTest()  {
		// GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		AddCash addCash = new AddCash();
		addCash.setUserGetter("pmbaminminb");
		addCash.setPhoneNumber("01001011");
		addCash.setAmount(290);
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		try {
			mockMvc.perform(post("/api/user/addCash").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)
					.content(objectMapper.writeValueAsString(addCash))).andDo(print()).andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 * @Description test controller for add cash with error
	 */
	@Test
	public void addCashErrorTest() {
		// GIVEN
		MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		AddCash addCash = new AddCash();
		addCash.setUserGetter(null);
		addCash.setPhoneNumber(null);
		addCash.setAmount(1);
		ObjectMapper objectMapper = new ObjectMapper();
		// WHEN
		// THEN
		try {
			mockMvc.perform(post("/api/user/addCash").accept(MediaType.APPLICATION_JSON).contentType(MEDIA_TYPE_JSON_UTF8)
					.content(objectMapper.writeValueAsString(addCash))).andDo(print()).andExpect(status().isBadRequest());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
