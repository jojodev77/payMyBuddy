package pmb.pmb.servicesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import pmb.pmb.dto.JwtUserResponse;
import pmb.pmb.dto.SignUpRequest;
import pmb.pmb.dto.SocialProvider;
import pmb.pmb.dto.UserReferenceTransaction;
import pmb.pmb.exception.UserAlreadyExistAuthenticationException;
import pmb.pmb.model.User;
import pmb.pmb.model.UserAccountInformations;
import pmb.pmb.repo.UserRepository;
import pmb.pmb.security.oauth2.user.OAuth2UserInfo;
import pmb.pmb.service.UserAccountRegistrationService;
import pmb.pmb.service.UserService;
import pmb.pmb.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Spy
	@InjectMocks
	private  UserServiceImpl userService = new UserServiceImpl();

	@Mock
	UserRepository userRepository;

	@Mock
	private UserAccountRegistrationService userAccountRegistrationService;
	
	
	String email = "admin@jojo";


	User user = new User();

	@BeforeEach
	private void setUpPerTest() {
		user.setDisplayName("jojo");
		user.setEmail("o@h.com");
		user.setPassword("testj");
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * @Description test new User with success
	 */
//	@Test
//	public void registerNewUserWithSuccessTest() {
//		// GIVEN
//		SignUpRequest signUpRequest = new SignUpRequest("1", "jojo", "o@h.com", "testj", SocialProvider.GOOGLE);
//	
//		
//		// WHEN
//		lenient().when(userAccountRegistrationService.attributeAccountInformations(any(User.class)))
//				.thenReturn(user.getUserAccountInformations());
//		userService.registerNewUser(signUpRequest);
//		// THEN
//		verify(userService).registerNewUser(signUpRequest);
//	}

	/**
	 * @Description test new User with Error
	 */
	@Test
	public void registerNewUserWithErrorWhenParamIsNullTest() {
		// GIVEN
		SignUpRequest signUpRequest = null;

		// WHEN
		lenient().when(userAccountRegistrationService.attributeAccountInformations(null))
				.thenReturn(null);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			userService.registerNewUser(null);
		});
		// THEN
		assertEquals("this informations for signup is null", exception.getMessage());

	}


//	/**
//	 * @Description test retrived user with this email with success
//	 */
	@Test
	public void findUserByEmailSuccessTest() {
		// GIVEN
		
		user.setDisplayName("jojo");
		user.setEmail("o@h.com");
		user.setPassword("testjQ");
		// WHEN
		lenient().when(userRepository.findByEmail(anyString())).thenReturn(user);
		// THEN
		userService.findUserByEmail(anyString());
		verify(userService).findUserByEmail(anyString());
	}

	/**
	 * @Description test retrived user with this email with error
	 */
	@Test
	public void findUserByEmailErrorWhenEmailIsNullTest() {
		// GIVEN
		String email = null;
		// WHEN
		lenient().when(userRepository.findByEmail(anyString())).thenReturn(user);
		// THEN
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			userService.findUserByEmail(email);
		});
		// THEN
		assertEquals("Error: email is null", exception.getMessage());
	}

	/**
	 * @Description test retrived user with this id with success
	 */
	@Test
	public void getUserByIdSuccessTest() {
		// GIVEN
		long id = 1;
		// WHEN
		lenient().when(userRepository.findUserById(anyLong())).thenReturn(user);
		// THEN
		userService.getUserById(id);
		verify(userService).getUserById(id);
	}

	/**
	 * @Description test retrived user with this id with error
	 */
	@Test
	public void getUserByIdErrorWhenEmailIsNullTest() {
		// GIVEN
		String email = null;
		// WHEN
		lenient().when(userRepository.findByEmail(anyString())).thenReturn(user);
		// THEN
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			userService.findUserByEmail(email);
		});
		// THEN
		assertEquals("Error: email is null", exception.getMessage());
	}

	/**
	 * @Description test method for get list of reference account transaction by
	 *              user with success
	 */
	@Test
	public void listReferenceTransactionSuccesstest() {
		// GIVEN
		ArrayList<UserReferenceTransaction> listUserReferenceTransactions = new ArrayList<>();
		List<User> listUser = new ArrayList<>();
		UserReferenceTransaction userReferenceTransaction = new UserReferenceTransaction();
		// WHEN
		lenient().when(userRepository.findAll()).thenReturn(listUser);
		// THEN
		userService.listReferenceTransaction();
		verify(userService).listReferenceTransaction();
	}

	/**
	 * @Description test method for get list of reference account transaction by
	 *              user with error
	 */
	@Test
	public void listReferenceTransactionErrorWhenListUserIsNulltest() {
//		// GIVEN
		ArrayList<UserReferenceTransaction> listUserReferenceTransactions = new ArrayList<>();
		List<User> listUser = new ArrayList<>();
		UserReferenceTransaction userReferenceTransaction = new UserReferenceTransaction();
//		// WHEN
		lenient().when(userRepository.findAll()).thenReturn(null);
//		// THEN
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			userService.listReferenceTransaction();
		});
//		// THEN
		assertEquals(null, exception.getMessage());
	}

	/**
	 * @Description test get jwt object with success
	 */
	@Test
	public void getJwtUserResponseByEmailSuccesTest() {
		// GIVEN
		String jwt = "GGREGGRGGEGEGGEGGG";
		String email = "o@h.com";
		UserAccountInformations userAccountInformation = new UserAccountInformations();
		userAccountInformation.setAccountReferenceTransaction("pmbt@tttotb");
		userAccountInformation.setSoldAccount(100);
		user.setId((long) 1);
		user.setDisplayName("totototo");
		user.setEmail("tt@tt.fr");
		user.setPassword("tototo");
		user.setUserAccountInformations(userAccountInformation);
		JwtUserResponse jwtUserResponse = new JwtUserResponse(jwt, (long) 1, email, "jojo", null, null);
		// WHEN
		lenient().when(userRepository.foundByEmail(anyString())).thenReturn(user);
		// THEN
		userService.getJwtUserResponseByEmail(jwt, email);
		verify(userService).getJwtUserResponseByEmail(jwt, email);
	}
	
	/**
	 * @Description test get jwt object with error
	 */
	@Test
	public void getJwtUserResponseByEmailErrorWhenEmailIsNullTest() {
		// GIVEN
		String jwt = "GGREGGRGGEGEGGEGGG";
		String email = null;
		JwtUserResponse jwtUserResponse = new JwtUserResponse(jwt, (long) 1, email, "jojo", null, null);
		// WHEN
		lenient().when(userRepository.foundByEmail(email)).thenReturn(user);
		// THEN
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			userService.getJwtUserResponseByEmail(jwt, email);
		});
		// THEN
		assertEquals("email is null", exception.getMessage());
	}
	
	/**
	 * @Description test get user by id with success
	 */
	@Test
	public void getUserByIdSuccesTest() {
		// GIVEN
		String jwt = "GGREGGRGGEGEGGEGGG";
		String email = "o@h.com";
		UserAccountInformations userAccountInformation = new UserAccountInformations();
		userAccountInformation.setAccountReferenceTransaction("pmbt@tttotb");
		userAccountInformation.setSoldAccount(100);
		user.setId((long) 1);
		user.setDisplayName("totototo");
		user.setEmail("tt@tt.fr");
		user.setPassword("tototo");
		user.setUserAccountInformations(userAccountInformation);
		JwtUserResponse jwtUserResponse = new JwtUserResponse(jwt, (long) 1, email, "jojo", null, null);
		// WHEN
		lenient().when(userRepository.findUserById(anyLong())).thenReturn(user);
		// THEN
		userService.findUserById(user.getId());
		verify(userService).findUserById(user.getId());
	}
	


}
