package pmb.pmb.servicesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import pmb.pmb.dto.AddCash;
import pmb.pmb.dto.HistoryResponse;
import pmb.pmb.dto.Buddy;
import pmb.pmb.dto.UserPartner;
import pmb.pmb.model.HistoryTransaction;
import pmb.pmb.model.User;
import pmb.pmb.model.UserAccountInformations;
import pmb.pmb.model.UserPartnerAccount;
import pmb.pmb.repo.UserRepository;
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

	List<User> listUser = new ArrayList<>();

	Buddy buddy = new Buddy();

	@BeforeEach
	private void setUpPerTest() {

		MockitoAnnotations.initMocks(this);
	}

	/**
	 * @Description test method for add a buddy whith success
	 */
	@Test
	public void addUserBuddySuccesTest() {
//		//GIVEN
		Buddy bud = new Buddy();
		UserAccountInformations userAccountInformation = new UserAccountInformations();
		userAccountInformation.setAccountReferenceTransaction("pmbt@tttotb");
		userAccountInformation.setSoldAccount(100);
		bud.setUserGetter("pmbtta@tatb");
		bud.setUserSetter("pmbt@tttotb");
		user.setId((long) 1);
		user.setDisplayName("totototo");
		user.setEmail("tt@tt.fr");
		user.setPassword("tototo");
		user.setUserAccountInformations(userAccountInformation);
		UserAccountInformations userAccountInformation1 = new UserAccountInformations();
		userAccountInformation1.setAccountReferenceTransaction("pmbtta@tatb");
		userAccountInformation1.setSoldAccount(100);
		User user1 = new User();
		user1.setId((long) 3);
		user1.setDisplayName("totototo");
		user1.setEmail("pmbaminminb");
		user1.setPassword("tototo");
		user1.setUserAccountInformations(userAccountInformation1);
		listUser.add(user1);
		listUser.add(user);
//		//WHEN
		// lenient().when(userRepository.findAll()).thenReturn(listUser);
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user);
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user1);
//		//THEN
		transactionService.addUserBuddy(bud);
		verify(transactionService).addUserBuddy(bud);

	}

	/**
	 * @Description test method for add a buddy whith error
	 */
	@Test
	public void addUserBuddyErrorWhenBuddyIsNullTest() {
		// GIVEN
		Buddy buddy = new Buddy();
		buddy.setUserGetter(null);
		String message = "";
		user.setDisplayName("jojo");
		user.setEmail("o@h.com");
		user.setPassword("testjQ");
		user1.setDisplayName("jojoQ");
		user1.setEmail("o@h.comQ");
		user1.setPassword("testjQ");
		listUser.add(user);
		listUser.add(user1);
		List<User> listUser = new ArrayList<>();
		// WHEN
		lenient().when(userRepository.findAll()).thenReturn(listUser);
		lenient().when(userRepository.findByUserReferenceTransaction(null)).thenReturn(user);
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
		// GIVEN
		Buddy buddy = new Buddy();
		buddy.setUserGetter("pmbfrtest");
		buddy.setUserSetter("pmbaminminb");
		String message = "";
		user.setDisplayName("jojo");
		user.setEmail("o@h.com");
		user.setPassword("testjQ");
		user1.setDisplayName("jojoQ");
		user1.setEmail("o@h.comQ");
		user1.setPassword("testjQ");
		List<User> listUser = null;
		// WHEN
		lenient().when(userRepository.findAll()).thenReturn(listUser);
		lenient().when(userRepository.findByUserReferenceTransaction(null)).thenReturn(user);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.addUserBuddy(buddy);
		});
		// THEN
		assertEquals("user setter not found", exception.getMessage());
	}

	/**
	 * @Description test method for start transaction with success
	 */
	@Test
	public void requestTransactionSuccessTest() {
		// GIVEN
		User user = new User();
		buddy.setUserGetter("pmbaminminb");
		buddy.setUserSetter("pmbt@tttotb");
		buddy.setAmount(10);
		String mess = "";
		user.setDisplayName("jojo");
		user.setEmail("o@h.com");
		user.setPassword("testjQ");
		UserAccountInformations uai = new UserAccountInformations();
		HistoryTransaction ht = new HistoryTransaction();
		List<HistoryTransaction> lht = new ArrayList<>();
		ht.setDisplayName("hhh");
		ht.setSoldAccount(1233);
		lht.add(ht);
		uai.setHistoryTransaction(lht);
		uai.setAccountReferenceTransaction("pmbt@tttotb");
		uai.setSoldAccount(200);
		user.setUserAccountInformations(uai);
		UserAccountInformations uais = new UserAccountInformations();

		uais.setAccountReferenceTransaction("pmbaminminb");
		uais.setSoldAccount(200);
		user1.setUserAccountInformations(uais);
		user1.setDisplayName("jojoQ");
		user1.setEmail("o@h.comQ");
		user1.setPassword("testjQ");
//		//WHEN
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user);
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user1);
//
//		//THEN
		transactionService.requestTransaction(buddy);
		verify(transactionService).requestTransaction(buddy);
	}

	/**
	 * @Description test method for start transaction with error
	 */
	@Test
	public void requestTransactionErrorWhenBuddyGetterIsNullTest() {
		// GIVEN
		Buddy buddy = new Buddy();
		buddy.setUserGetter(null);
		buddy.setUserSetter("pmbaminminb");
		buddy.setAmount(10);
		String mess = "";
		// WHEN
		lenient().when(userRepository.findByUserReferenceTransaction(null)).thenReturn(null);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.requestTransaction(buddy);
		});
		// THEN
		assertEquals("buddy informations is null", exception.getMessage());
	}

	/**
	 * @Description test method for start transaction with error
	 */
	@Test
	public void requestTransactionErrorWhenBuddySetterIsNullTest() {
		// GIVEN
		Buddy buddy = new Buddy();
		buddy.setUserGetter("pmbfrtest");
		buddy.setUserSetter(null);
		buddy.setAmount(10);

		String mess = "";
		// WHEN
		lenient().when(userRepository.findByUserReferenceTransaction(null)).thenReturn(null);
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
	public void requestTransactionErrorWhenUserSoldAccountIsMoreOnTransfertNecessaryIsNullTest() {
		// GIVEN
		Buddy buddy = new Buddy();
		buddy.setUserGetter("pmbfrtest");
		buddy.setUserSetter(null);
		buddy.setAmount(10);
		user.setDisplayName("jojo");
		user.setEmail("o@h.com");
		user.setPassword("testjQ");
		user1.setDisplayName("jojoQ");
		user1.setEmail("o@h.comQ");
		user1.setPassword("testjQ");

		String mess = "";
		// WHEN
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user);
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user1);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.requestTransaction(buddy);
		});
		// THEN
		assertEquals("no user setter found", exception.getMessage());
	}

	/**
	 * @Description test method for get list user parter with error
	 */
	@Test
	public void listUserPartnerErrorWhenUserIsNullTest() {
		// GIVEN
		user.setId((long) 2);
		user.setDisplayName("totototo");
		user.setEmail("tt@tt.fr");
		user.setPassword("tototo");
		Set<UserPartner> listUserPartner = new HashSet<UserPartner>();
		// WHEN

		lenient().when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		// THEN
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.listUserPartner((long) 1);
		});
		// THEN
		assertEquals("not list user found", exception.getMessage());
	}

	/**
	 * @Description method for add cash with success
	 */
	@Test
	public void addCashSuccessTest() {
		// GIVEN
		UserAccountInformations userAccountInformation = new UserAccountInformations();
		userAccountInformation.setAccountReferenceTransaction("pmbt@tttotb");
		userAccountInformation.setSoldAccount(100);
		buddy.setUserGetter("pmbaminminb");
		buddy.setUserSetter("pmbt@tttotb");
		buddy.setAmount(10);
		user.setId((long) 1);
		user.setDisplayName("totototo");
		user.setEmail("tt@tt.fr");
		user.setPassword("tototo");
		user.setUserAccountInformations(userAccountInformation);
		AddCash cash = new AddCash();
		cash.setAmount(400);
		cash.setAmount(700);
		cash.setUserGetter("pmbt@tttotb");
		String mess = "";
		// WHEN
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user);
		// THEN
		transactionService.addCash(cash);
		verify(transactionService).addCash(cash);
	}

	/**
	 * @Description method for add cash with error
	 */
	@Test
	public void addCashErrorWhenCashIsNullTest() {
		// GIVEN
		AddCash cash = null;
		String mess = "";
		user.setDisplayName("jojo");
		user.setEmail("o@h.com");
		user.setPassword("testjQ");
		user1.setDisplayName("jojoQ");
		user1.setEmail("o@h.comQ");
		user1.setPassword("testjQ");
		listUser.add(user);
		listUser.add(user1);
		// WHEN
		// lenient().when(transactionService.addCash(cash)).thenReturn(mess);
		lenient().when(userRepository.findByUserReferenceTransaction(null)).thenReturn(user);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.addCash(cash);
		});
		// THEN
		assertEquals(null, exception.getMessage());
	}

	/**
	 * @Description method for add cash with error
	 */
	@Test
	public void addCashErrorWhenUserIsNullTest() {
		// GIVEN
		AddCash cash = new AddCash();
		cash.setAmount(10);
		cash.setAmount(010101010);
		String mess = "";
		// WHEN
		// lenient().when(transactionService.addCash(cash)).thenReturn(mess);
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
	@Test
	public void getListHistorySuccessTest() {
		// GIVEN
		UserAccountInformations userAccountInformation = new UserAccountInformations();
		userAccountInformation.setAccountReferenceTransaction("pmbt@tttotb");
		userAccountInformation.setSoldAccount(100);
		buddy.setUserGetter("pmbaminminb");
		buddy.setUserSetter("pmbt@tttotb");
		buddy.setAmount(10);
		user.setId((long) 1);
		user.setDisplayName("totototo");
		user.setEmail("tt@tt.fr");
		user.setPassword("tototo");
		user.setUserAccountInformations(userAccountInformation);
		List<HistoryTransaction> lht = new ArrayList<>();
		HistoryTransaction ht = new HistoryTransaction();
		HistoryTransaction ht1 = new HistoryTransaction();
		ht.setAccount_reference_transaction("pmbaminminb");
		ht.setSoldAccount(20000);
		ht.setDisplayName("jojo");
		ht1.setAccount_reference_transaction("pmbaminminb");
		ht1.setSoldAccount(20000);
		ht1.setDisplayName("jojo");
		lht.add(ht);
		lht.add(ht1);
		user.getUserAccountInformations().setHistoryTransaction(lht);
		// WHEN
		when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user);
		// THEN
		transactionService.getListHistory(buddy);
		verify(transactionService).getListHistory(buddy);
	}

	/**
	 * @Description method for get list history whith error
	 */
	@Test
	public void getListHistoryErrorWhenBuddyIsNullTest() {
		// GIVEN
		Buddy buddy = new Buddy();
		UserAccountInformations userAccountInformation = new UserAccountInformations();
		UserPartner userPartner = new UserPartner();
		userPartner.setDisplayName("ddd");
		userPartner.setUserRefTransaction("pmbt@tttotb");
		Set<UserPartner> up = new HashSet<UserPartner>();
		up.add(userPartner);
		userAccountInformation.setAccountReferenceTransaction("pmbt@tttotb");
		userAccountInformation.setSoldAccount(100);

		user.setId((long) 1);
		user.setDisplayName("totototo");
		user.setEmail("tt@tt.fr");
		user.setPassword("tototo");
		user.setUserAccountInformations(userAccountInformation);
		user1.setDisplayName("jojoQ");
		user1.setEmail("o@h.comQ");
		user1.setPassword("testjQ");
		listUser.add(user);
		listUser.add(user1);
		Set<HistoryResponse> listHistoryResponse = new HashSet<>();
		// WHEN
		// lenient().when(transactionService.getListHistory(buddy)).thenReturn(listHistoryResponse);
		lenient().when(userRepository.findByUserReferenceTransaction(buddy.getUserGetter())).thenReturn(user);
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.getListHistory(buddy);
		});
		// THEN
		assertEquals(null, exception.getMessage());
	}

	/**
	 * @Description test method for delete buddy with success
	 */
	@Test
	public void deleteBuddyWithSuccess() {
		// GIVEN

		UserAccountInformations userAccountInformation = new UserAccountInformations();
		userAccountInformation.setAccountReferenceTransaction("pmbt@tttotb");
		userAccountInformation.setSoldAccount(100);
		buddy.setUserGetter("pmbtta@tatb");
		buddy.setUserSetter("pmbt@tttotb");
		user.setId((long) 1);
		user.setDisplayName("totototo");
		user.setEmail("tt@tt.fr");
		user.setPassword("tototo");
		user.setUserAccountInformations(userAccountInformation);
		UserAccountInformations userAccountInformation1 = new UserAccountInformations();
		userAccountInformation1.setAccountReferenceTransaction("pmbtta@tatb");
		userAccountInformation1.setSoldAccount(100);
		User user1 = new User();
		user1.setId((long) 3);
		user1.setDisplayName("totototo");
		user1.setEmail("pmbaminminb");
		user1.setPassword("tototo");
		user1.setUserAccountInformations(userAccountInformation1);
		listUser.add(user1);
		listUser.add(user);
		UserPartnerAccount upa = new UserPartnerAccount();
		upa.setUserRefTransaction("pmbaminminb");
		upa.setDisplayName("admin");
		upa.setUserAccountInformations(userAccountInformation);
		Set<UserPartnerAccount> supa = new HashSet<>();
		supa.add(upa);
		user.getUserAccountInformations().setUserPartner_account(supa);
		UserPartnerAccount upa1 = new UserPartnerAccount();
		upa1.setUserRefTransaction("pmbaminminb");
		upa1.setDisplayName("admin");
		upa1.setUserAccountInformations(userAccountInformation);
		Set<UserPartnerAccount> supa1 = new HashSet<>();
		supa1.add(upa1);
		user1.getUserAccountInformations().setUserPartner_account(supa1);
		// WHEN
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user);
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user1);
		lenient().when(userRepository.save(any())).thenReturn(user);
		// THEN
		transactionService.deleteBuddy(buddy);
		verify(transactionService).deleteBuddy(buddy);
	}

	/**
	 * @Description test method for delete buddy with error
	 */
	@Test
	public void deleteBuddyWithErrorWhenbuddyIsNull() {
		// GIVEN

		buddy.setUserGetter(null);
		buddy.setUserSetter(null);
		user.setId((long) 1);
		user.setDisplayName("totototo");
		user.setEmail("tt@tt.fr");
		user.setPassword("tototo");
		// WHEN
		lenient().when(userRepository.findByUserReferenceTransaction(null)).thenReturn(null);
		// THEN
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.deleteBuddy(buddy);
		});
		// THEN
		assertEquals("not user found", exception.getMessage());
	}

	/**
	 * @Description test method for get account situation with success
	 */
	@Test
	public void accountSituationWithSuccess() {
		// GIVEN
		UserAccountInformations userAccountInformation = new UserAccountInformations();
		userAccountInformation.setAccountReferenceTransaction("pmbt@tttotb");
		userAccountInformation.setSoldAccount(100);
		buddy.setUserGetter("pmbaminminb");
		buddy.setUserSetter("pmbt@tttotb");
		buddy.setAmount(10);
		user.setId((long) 1);
		user.setDisplayName("totototo");
		user.setEmail("tt@tt.fr");
		user.setPassword("tototo");
		user.setUserAccountInformations(userAccountInformation);
		// WHEN
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user);
		// THEN
		transactionService.accountSituation(buddy);
		verify(transactionService).accountSituation(buddy);
	}

	/**
	 * @Description test method for get account situation with error
	 */
	@Test
	public void accountSituationWithErrorWhenUserIsNotFound() {
		// GIVEN
		UserAccountInformations userAccountInformation = new UserAccountInformations();
		userAccountInformation.setAccountReferenceTransaction("pmbt@tttotb");
		userAccountInformation.setSoldAccount(100);
		buddy.setUserGetter("pmbaminminb");
		buddy.setUserSetter("pmbt@tttotb");
		buddy.setAmount(10);
		user.setId((long) 1);
		user.setDisplayName("totototo");
		user.setEmail("tt@tt.fr");
		user.setPassword("tototo");
		user.setUserAccountInformations(userAccountInformation);
		// WHEN
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(null);
		// THEN
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			transactionService.accountSituation(buddy);
		});
		// THEN
		assertEquals("not user found", exception.getMessage());
	}
}
