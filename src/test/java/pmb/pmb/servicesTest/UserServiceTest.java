package pmb.pmb.servicesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import pmb.pmb.dto.JwtUserResponse;
import pmb.pmb.dto.SignUpRequest;
import pmb.pmb.dto.UserReferenceTransaction;
import pmb.pmb.exception.UserAlreadyExistAuthenticationException;
import pmb.pmb.model.User;
import pmb.pmb.repo.UserRepository;
import pmb.pmb.service.UserAccountRegistrationService;
import pmb.pmb.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Spy
	@InjectMocks
	private static UserService userService;

	@Mock
	UserRepository userRepository;

	@Mock
	private UserAccountRegistrationService userAccountRegistrationService;

	User user = new User();

	@BeforeEach
	private void setUpPerTest() {
		user.setDisplayName("jojo");
		user.setEmail("o@h.com");
		user.setPassword("testj");
	}

	/**
	 * @Description test new User with success
	 */
	@Test
	public void registerNewUserWithSuccessTest() {
		// GIVEN
		SignUpRequest signUpRequest = new SignUpRequest("1", "jojo", "o@h.com", "testj", null);

		// WHEN
		lenient().when(userService.registerNewUser(signUpRequest)).thenReturn(user);
		lenient().when(userAccountRegistrationService.attributeAccountInformations(user))
				.thenReturn(user.getUserAccountInformations());
		userService.registerNewUser(signUpRequest);
		// THEN
		verify(userService).registerNewUser(signUpRequest);
	}

	/**
	 * @Description test new User with Error
	 */
	@Test
	public void registerNewUserWithErrorWhenParamIsNullTest() {
		// GIVEN
		SignUpRequest signUpRequest = null;

		// WHEN
		lenient().when(userService.registerNewUser(signUpRequest)).thenReturn(user);
		lenient().when(userAccountRegistrationService.attributeAccountInformations(user))
				.thenReturn(user.getUserAccountInformations());
		userService.registerNewUser(signUpRequest);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			userService.registerNewUser(signUpRequest);
		});
		// THEN
		assertEquals("this informations for signup is null", exception.getMessage());

	}

	/**
	 * @Description test new User with Error
	 */
	@Test
	public void registerNewUserWithErrorWhenUserExistInDBTest() {
		// GIVEN
		SignUpRequest signUpRequest = new SignUpRequest("1", "Admin", "admin@jojo", "admin@", null);

		// WHEN
		lenient().when(userService.registerNewUser(signUpRequest)).thenReturn(user);
		lenient().when(userAccountRegistrationService.attributeAccountInformations(user))
				.thenReturn(user.getUserAccountInformations());
		userService.registerNewUser(signUpRequest);
		UserAlreadyExistAuthenticationException exception = assertThrows(UserAlreadyExistAuthenticationException.class,
				() -> {
					userService.registerNewUser(signUpRequest);
				});
		// THEN
		assertEquals("User with email id " + signUpRequest.getEmail() + " already exist", exception.getMessage());

	}

	/**
	 * @Description test retrived user with this email with success
	 */
	@Test
	public void findUserByEmailSuccessTest() {
		// GIVEN
		String email = "admin@jojo";
		// WHEN
		lenient().when(userService.findUserByEmail(email)).thenReturn(user);
		lenient().when(userRepository.findByEmail(anyString())).thenReturn(user);
		// THEN
		userService.findUserByEmail(email);
		verify(userService).findUserByEmail(email);
	}

	/**
	 * @Description test retrived user with this email with error
	 */
	@Test
	public void findUserByEmailErrorWhenEmailIsNullTest() {
		// GIVEN
		String email = null;
		// WHEN
		lenient().when(userService.findUserByEmail(email)).thenReturn(user);
		lenient().when(userRepository.findByEmail(anyString())).thenReturn(user);
		// THEN
		userService.findUserByEmail(email);
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
		lenient().when(userService.getUserById(id)).thenReturn(user);
		lenient().when(userRepository.findUserById(id)).thenReturn(user);
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
		lenient().when(userService.findUserByEmail(email)).thenReturn(user);
		lenient().when(userRepository.findByEmail(anyString())).thenReturn(user);
		// THEN
		userService.findUserByEmail(email);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			userService.findUserByEmail(email);
		});
		// THEN
		assertEquals("user not found", exception.getMessage());
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
		// GIVEN
		ArrayList<UserReferenceTransaction> listUserReferenceTransactions = new ArrayList<>();
		List<User> listUser = new ArrayList<>();
		UserReferenceTransaction userReferenceTransaction = new UserReferenceTransaction();
		// WHEN
		lenient().when(userRepository.findAll()).thenReturn(null);
		// THEN
		userService.listReferenceTransaction();
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			userService.listReferenceTransaction();
		});
		// THEN
		assertEquals("not list user found", exception.getMessage());
	}

	/**
	 * @Description test get jwt object with success
	 */
	@Test
	public void getJwtUserResponseByEmailSuccesTest() {
		// GIVEN
		String jwt = "GGREGGRGGEGEGGEGGG";
		String email = "o@h.com";
		JwtUserResponse jwtUserResponse = new JwtUserResponse(jwt, (long) 1, email, "jojo", null, null);
		// WHEN
		lenient().when(userService.getJwtUserResponseByEmail(jwt, email)).thenReturn(jwtUserResponse);
		lenient().when(userRepository.foundByEmail(email)).thenReturn(user);
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
		lenient().when(userService.getJwtUserResponseByEmail(jwt, email)).thenReturn(jwtUserResponse);
		lenient().when(userRepository.foundByEmail(email)).thenReturn(user);
		// THEN
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			userService.getJwtUserResponseByEmail(jwt, email);
		});
		// THEN
		assertEquals("email is null", exception.getMessage());
	}

}
