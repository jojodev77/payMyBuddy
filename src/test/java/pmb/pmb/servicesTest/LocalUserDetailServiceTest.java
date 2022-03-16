package pmb.pmb.servicesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pmb.pmb.model.User;
import pmb.pmb.service.LocalUserDetailService;
import pmb.pmb.service.UserService;

@ExtendWith(MockitoExtension.class)
public class LocalUserDetailServiceTest {

	@InjectMocks
	LocalUserDetailService localUserDetailService;

	@Mock
	UserService userService;

	User user = new User();

	@BeforeEach
	private void setUpPerTest() {
		user.setDisplayName("jojo");
		user.setEmail("o@h.com");
		user.setPassword("testj");
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * @Description test method for retrived user with this name with errors
	 */
	@Test
	public void loadUserByUsernameErrorWhenUserIsNotFoundTets() {
		// GIVEN
		String email = "grgr@ff.com";
		// WHEN
		lenient().when(userService.findUserByEmail(email)).thenReturn(user);
		// THEN
		UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
			localUserDetailService.loadUserByUsername(null);
		});
		// THEN
		assertEquals("User " + null + " was not found in the database", exception.getMessage());
	}

	/**
	 * @Description test method for retrived user with this id with success
	 */
}
