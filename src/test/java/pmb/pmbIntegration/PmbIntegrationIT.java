package pmb.pmbIntegration;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
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
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import pmb.pmb.dto.AddBuddy;
import pmb.pmb.dto.UserBuddy;
import pmb.pmb.model.HistoryTransaction;
import pmb.pmb.model.User;
import pmb.pmb.model.UserAccountInformations;
import pmb.pmb.repo.UserRepository;
import pmb.pmb.service.TransactionService;
import pmb.pmb.service.UserAccountRegistrationService;
import pmb.pmb.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PmbIntegrationIT {

	@Spy
	@InjectMocks
	private static TransactionService transactionService = new TransactionService();

	User user = new User();
	User user1 = new User();
	UserBuddy buddy = new UserBuddy();
	AddBuddy ab = new AddBuddy();
	
	@Mock
	UserRepository userRepository;
	
	@Spy
	@InjectMocks
	private  UserServiceImpl userService = new UserServiceImpl();
	
	@Mock
	private UserAccountRegistrationService userAccountRegistrationService;

	@BeforeEach
	private void setUpPerTest() {

		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * @Description test integration: (Add cash + Add Buddy + Add Transaction + get history transaction
	 */
	@Test
	public void testScenarioForTransaction() {
		//GIVEN
		AddBuddy bud = new AddBuddy();
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
		uai.setAccountReferenceTransaction("pmbt@tttotb");
		uai.setSoldAccount(100);
		bud.setUserGetter("pmbtta@tatb");
		bud.setUserSetter("pmbt@tttotb");
		buddy.setUserGetter("pmbtta@tatb");
		buddy.setUserSetter("pmbt@tttotb");
		buddy.setAmount(10);
		user.setId((long) 1);
		user.setDisplayName("totototo");
		user.setEmail("tt@tt.fr");
		user.setPassword("tototo");
		user.setUserAccountInformations(uai);
		UserAccountInformations userAccountInformation1 = new UserAccountInformations();
		userAccountInformation1.setAccountReferenceTransaction("pmbtta@tatb");
		userAccountInformation1.setSoldAccount(100);
		User user1 = new User();
		user1.setId((long) 3);
		user1.setDisplayName("totototo");
		user1.setEmail("pmbaminminb");
		user1.setPassword("tototo");
		user1.setUserAccountInformations(userAccountInformation1);
		
		//when
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user);
		lenient().when(userRepository.findByUserReferenceTransaction(anyString())).thenReturn(user1);
		
		//THEN
		transactionService.addUserBuddy(bud);
		verify(transactionService).addUserBuddy(bud);
		transactionService.requestTransaction(buddy);
		verify(transactionService).requestTransaction(buddy);
		transactionService.getListHistory(buddy);
		verify(transactionService).getListHistory(buddy);
	}
	
	
	

}
