package pmb.pmb.servicesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import pmb.pmb.dto.AddBuddy;
import pmb.pmb.dto.AddCash;
import pmb.pmb.dto.HistoryResponse;
import pmb.pmb.dto.UserBuddy;
import pmb.pmb.dto.UserPartner;
import pmb.pmb.model.User;
import pmb.pmb.repo.UserRepository;
import pmb.pmb.service.TransacService;
import pmb.pmb.service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransacServiceTest {

	@Spy
	@InjectMocks
	private static TransactionService transactionService = new TransactionService();
	
	@Mock
	UserRepository userRepository;

	User user = new User();
	User user1 = new User();

	@BeforeEach
	private void setUpPerTest() {
		user.setDisplayName("jojo");
		user.setEmail("o@h.com");
		user.setPassword("testjQ");
		user1.setDisplayName("jojoQ");
		user1.setEmail("o@h.comQ");
		user1.setPassword("testjQ");
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * @Description test method for add a buddy whith success
	 */
	@Test
	public void addUserBuddySuccesTest() {
		//GIVEN
		AddBuddy buddy = new AddBuddy();
		buddy.setUserGetter("pmbfrtest");
		buddy.setUserSetter("pmbaminminb");
		String message = "";
		List<User> listUser = new ArrayList<>();
		//WHEN
		lenient().when(userRepository.findAll()).thenReturn(listUser);
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user);
		//THEN
		transactionService.addUserBuddy(buddy);
		verify(transactionService).addUserBuddy(buddy);
	}
	
	/**
	 * @Description test method for add a buddy whith error
	 */
	@Test
	public void addUserBuddyErrorWhenBuddyIsNullTest() {
		//GIVEN
		AddBuddy buddy = null;
		String message = "";
		List<User> listUser = new ArrayList<>();
		//WHEN
	//	lenient().when(transactionService.addUserBuddy(buddy)).thenReturn(message);
		lenient().when(userRepository.findAll()).thenReturn(listUser);
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.addUserBuddy(buddy);
		});
		// THEN
		assertEquals("buddy informations is null", exception.getMessage());
	}
	
	/**
	 * @Description test method for add a buddy whith error
	 */
	@Test
	public void addUserBuddyErrorWhenListUserTest() {
		//GIVEN
		AddBuddy buddy = new AddBuddy();
		buddy.setUserGetter("pmbfrtest");
		buddy.setUserSetter("pmbaminminb");
		String message = "";
		List<User> listUser = null;
		//WHEN
		lenient().when(transactionService.addUserBuddy(buddy)).thenReturn(message);
		lenient().when(userRepository.findAll()).thenReturn(listUser);
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.addUserBuddy(buddy);
		});
		// THEN
		assertEquals("Error: not account situation found", exception.getMessage());
	}
	
	/**
	 * @Description test method for start transaction with success
	 */
	@Test
	public void requestTransactionSuccessTest() {
		//GIVEN
		UserBuddy buddy = new UserBuddy();
		buddy.setUserGetter("pmbfrtest");
		buddy.setUserSetter("pmbaminminb");
		buddy.setAmount(10);
		String mess = "";
		//WHEN
		lenient().when(transactionService.requestTransaction(buddy)).thenReturn(mess);
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user);
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user1);
		lenient().when(userRepository.save(user));
		lenient().when(userRepository.save(user1));
		//THEN
		transactionService.requestTransaction(buddy);
		verify(transactionService).requestTransaction(buddy);
	}
	
	/**
	 * @Description test method for start transaction with error
	 */
	@Test
	public void requestTransactionErrorWhenBuddyGetterIsNullTest() {
		//GIVEN
		UserBuddy buddy = new UserBuddy();
		buddy.setUserGetter(null);
		buddy.setUserSetter("pmbaminminb");
		buddy.setAmount(10);
		String mess = "";
		//WHEN
	//	lenient().when(transactionService.requestTransaction(buddy)).thenReturn(mess);
		lenient().when(userRepository.findByUserReferenceTransaction(buddy.getUserSetter())).thenReturn(user);
		lenient().when(userRepository.findByUserReferenceTransaction(buddy.getUserGetter())).thenReturn(user1);
		lenient().when(userRepository.save(user));
		lenient().when(userRepository.save(user1));
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.requestTransaction(buddy);
		});
		// THEN
		assertEquals("no user getter found", exception.getMessage());
	}
	
	/**
	 * @Description test method for start transaction with error
	 */
	@Test
	public void requestTransactionErrorWhenBuddySetterIsNullTest() {
		//GIVEN
		UserBuddy buddy = new UserBuddy();
		buddy.setUserGetter("pmbfrtest");
		buddy.setUserSetter(null);
		buddy.setAmount(10);
		
		String mess = "";
		//WHEN
	//	lenient().when(transactionService.requestTransaction(buddy)).thenReturn(mess);
		lenient().when(userRepository.findByUserReferenceTransaction(buddy.getUserSetter())).thenReturn(user);
		lenient().when(userRepository.findByUserReferenceTransaction(buddy.getUserGetter())).thenReturn(user1);
		lenient().when(userRepository.save(user));
		lenient().when(userRepository.save(user1));
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.requestTransaction(buddy);
		});
		// THEN
		assertEquals("no user setter found", exception.getMessage());
	}
	
	/**
	 * @Description test method for start transaction with error
	 */
	@Test
	public void requestTransactionErrorWhenUserSoldAccountIsMoreOnTransfertNecessaryIsNullTest() {
		//GIVEN
		UserBuddy buddy = new UserBuddy();
		buddy.setUserGetter("pmbfrtest");
		buddy.setUserSetter(null);
		buddy.setAmount(10);
		user.getUserAccountInformations().setSoldAccount(0);
		String mess = "";
		//WHEN
	//	lenient().when(transactionService.requestTransaction(buddy)).thenReturn(mess);
		lenient().when(userRepository.findByUserReferenceTransaction(buddy.getUserSetter())).thenReturn(user);
		lenient().when(userRepository.findByUserReferenceTransaction(buddy.getUserGetter())).thenReturn(user1);
		lenient().when(userRepository.save(user));
		lenient().when(userRepository.save(user1));
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.requestTransaction(buddy);
		});
		// THEN
		assertEquals("you do not have the necessary means", exception.getMessage());
	}
	
	/**
	 * @Description test method for get list user parter with success
	 */
	public void listUserPartnerSuccesTest() {
		//GIVEN
		Set<UserPartner> listUserPartner = new HashSet<UserPartner>();
		//WHEN
	//	lenient().when(transactionService.listUserPartner(user.getId())).thenReturn(listUserPartner);
		lenient().when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		//THEN
		transactionService.listUserPartner(user.getId());
		verify(transactionService).listUserPartner(user.getId());
	}
	

	/**
	 * @Description test method for get list user parter with error
	 */
	public void listUserPartnerErrorWhenUserIsNullTest() {
		//GIVEN
		Set<UserPartner> listUserPartner = new HashSet<UserPartner>();
		//WHEN
		lenient().when(transactionService.listUserPartner(user.getId())).thenReturn(listUserPartner);
		lenient().when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		//THEN
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.listUserPartner(user.getId());
		});
		// THEN
		assertEquals("not user found", exception.getMessage());
	}
	
	/**
	 * @Description method for add cash with success
	 */
	public void addCashSuccessTest() {
		//GIVEN
		AddCash cash = new AddCash();
		cash.setAmount(10);
		cash.setAmount(010101010);
		String mess = "";
		//WHEN
	//	lenient().when(transactionService.addCash(cash)).thenReturn(mess);
		lenient().when(userRepository.findByUserReferenceTransaction(cash.getUserGetter())).thenReturn(user);
		//THEN
		transactionService.addCash(cash);
		verify(transactionService).addCash(cash);
	}
	
	/**
	 * @Description method for add cash with error
	 */
	public void addCashErrorWhenCashIsNullTest() {
		//GIVEN
		AddCash cash = null;
		String mess = "";
		//WHEN
	//	lenient().when(transactionService.addCash(cash)).thenReturn(mess);
		lenient().when(userRepository.findByUserReferenceTransaction(cash.getUserGetter())).thenReturn(user);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.addCash(cash);
		});
		// THEN
		assertEquals("not money deteted", exception.getMessage());
	}
	
	/**
	 * @Description method for add cash with error
	 */
	public void addCashErrorWhenUserIsNullTest() {
		//GIVEN
		AddCash cash = new AddCash();
		cash.setAmount(10);
		cash.setAmount(010101010);
		String mess = "";
		//WHEN
	//	lenient().when(transactionService.addCash(cash)).thenReturn(mess);
		lenient().when(userRepository.findByUserReferenceTransaction(cash.getUserGetter())).thenReturn(null);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.addCash(cash);
		});
		// THEN
		assertEquals("not account situation found", exception.getMessage());
	}
	
	/**
	 * @Description method for get list history with success
	 */
	public void getListHistorySuccessTest() {
		//GIVEN
		UserBuddy buddy = new UserBuddy();
		buddy.setUserGetter("pmbfrtest");
		buddy.setUserSetter("pmbaminminb");
		buddy.setAmount(10);
		Set<HistoryResponse> listHistoryResponse = new HashSet<>();
		//WHEN
	//	lenient().when(transactionService.getListHistory(buddy)).thenReturn(listHistoryResponse);
		lenient().when(userRepository.findByUserReferenceTransaction(buddy.getUserGetter())).thenReturn(user);
		//THEN
		transactionService.getListHistory(buddy);
		verify(transactionService).getListHistory(buddy);
	}
	
	/**
	 * @Description method for get list history whith error
	 */
	public void getListHistoryErrorWhenBuddyIsNullTest() {
		//GIVEN
		UserBuddy buddy = null;
		Set<HistoryResponse> listHistoryResponse = new HashSet<>();
		//WHEN
	//	lenient().when(transactionService.getListHistory(buddy)).thenReturn(listHistoryResponse);
		lenient().when(userRepository.findByUserReferenceTransaction(buddy.getUserGetter())).thenReturn(user);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.getListHistory(buddy);
		});
		// THEN
		assertEquals("buddy informations is null", exception.getMessage());
	}
}
