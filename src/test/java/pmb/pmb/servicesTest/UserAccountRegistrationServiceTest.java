package pmb.pmb.servicesTest;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.InstanceOf;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.instanceOf;

import pmb.pmb.model.User;
import pmb.pmb.model.UserAccountInformations;
import pmb.pmb.service.UserAccountRegistrationService;

@ExtendWith(MockitoExtension.class)
public class UserAccountRegistrationServiceTest {

	@InjectMocks
	private  UserAccountRegistrationService userAccountRegistrationService = new UserAccountRegistrationService();

	User user = new User();
	
	@Mock
	User us = new User();
	


	@BeforeEach
	private void setUpPerTest() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * @Description test method for attribute userAccountInformations with success
	 */
	@Test
	public void attributeAccountInformationsSuccessTest() {
		// GIVEN
		UserAccountInformations userAccountInformations = new UserAccountInformations();
		User user = new User();
		user.setId((long) 1);
		user.setDisplayName("totototo");
		user.setEmail("tt@tt.fr");
		user.setPassword("tototo");		User us = new User();
		us.setId((long) 1);
		us.setDisplayName("totototo");
		us.setEmail("tt@tt.fr");
		us.setPassword("tototo");
		String account_reference_transaction = "pmb" + user.getEmail().substring(0, 1) + user.getEmail().substring(2, 5) + user.getDisplayName().substring(2, 5) + "b";
		userAccountInformations.setAccountReferenceTransaction(account_reference_transaction);
		// WHEN
		// THEN
		userAccountRegistrationService.attributeAccountInformations(user);
		assertThat(userAccountRegistrationService.attributeAccountInformations(user),notNullValue());
	}

	/**
	 * @Description test method for attribute userAccountInformations with success
	 */
	@Test
	public void attributeAccountInformationsSuccessWithAssertResultTest() {
		// GIVEN
		UserAccountInformations userAccountInformations = new UserAccountInformations();
		user.setId((long) 1);
		user.setDisplayName("totototo");
		user.setEmail("tt@tt.fr");
		user.setPassword("tototo");
		String account_reference_transaction = "pmb" + user.getEmail().substring(0, 1) + user.getEmail().substring(2, 5) + user.getDisplayName().substring(2, 5) + "b";
		userAccountInformations.setAccountReferenceTransaction(account_reference_transaction);
		// WHEN
		assertEquals(userAccountInformations.getAccountReferenceTransaction(), userAccountRegistrationService.attributeAccountInformations(user).getAccountReferenceTransaction());
		// THEN
	}
}
