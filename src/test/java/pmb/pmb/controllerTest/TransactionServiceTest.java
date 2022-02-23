package pmb.pmb.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import antlr.collections.List;
import pmb.pmb.dto.AddBuddy;
import pmb.pmb.model.Role;
import pmb.pmb.model.User;
import pmb.pmb.model.UserAccountInformations;
import pmb.pmb.repo.UserAccountInfomationsRepository;
import pmb.pmb.repo.UserRepository;
import pmb.pmb.service.TransacService;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

	@Mock
	UserRepository userRepository;
	
	@Mock
	UserAccountInfomationsRepository userAccountInfomationsRepository;
	
	@Spy
	@InjectMocks
	private static TransacService transactionService;
	
	@BeforeEach
	private void setUpPerTest() {
	
	}
	
	/**
	 * @Description test method for add buddy with success
	 */
	@Test
	public void addUserBuddyWithSucces() {
		//GIVEN
		AddBuddy buddy  = new AddBuddy();
		buddy.setUserGetter("pmbt@tttotb");
		buddy.setUserSetter("pmbaminminb");
		String mess = "";
		ArrayList<User> userList = new ArrayList<>();
		User user = new User();
		Role role = new Role();
		UserAccountInformations userAccountInformations = new UserAccountInformations();
		userAccountInformations.setUser(user);
		userAccountInformations.setSoldAccount(20);
		userAccountInformations.setAccountReferenceTransaction("jmb67-GGG");
		role.setName("Role.ROLE_USER");
		user.setDisplayName("jojo");
		user.setEmail("j@g.com");
		user.setPassword("jonathan");
		user.getRoles().add(role);
		user.setUserAccountInformations(userAccountInformations);
		//WHEN
		lenient().when(transactionService.addUserBuddy(buddy)).thenReturn(mess);
		lenient().when(userRepository.findAll()).thenReturn(userList);
		lenient().when(userRepository.findByUserReferenceTransaction(buddy.getUserGetter())).thenReturn(user);
		//THEN
		transactionService.addUserBuddy(buddy);
		verify(transactionService).addUserBuddy(buddy);
		
	}
	
	/**
	 * @Description test method for add buddy with error
	 */
	@Test
	public void addUserBuddyWithError() {
		//GIVEN
		AddBuddy buddy  = new AddBuddy();
		buddy.setUserGetter("pmbt@tttotb");
		buddy.setUserSetter("pmbaminminb");
		String mess = "";
		ArrayList<User> userList = new ArrayList<>();
		//WHEN
		lenient().when(transactionService.addUserBuddy(buddy)).thenReturn(mess);
		lenient().when(userRepository.findAll()).thenReturn(userList);
		//THEN
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.addUserBuddy(buddy);
		});
		assertEquals("buddy informations is null", exception.getMessage());
		
	}
	

}
