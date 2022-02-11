package pmb.pmb.service;

import org.springframework.stereotype.Service;

import pmb.pmb.dto.SignUpRequest;
import pmb.pmb.model.User;
import pmb.pmb.model.UserAccountInformations;
import pmb.pmb.model.UserPersonnalInformations;

@Service
public class UserAccountRegistrationService {

	public UserAccountInformations attributeAccountInformations(User userPersonnalInformations_dto) {
		UserAccountInformations userAccountInformations = new UserAccountInformations();
		userAccountInformations.setSoldAccount(0);
		String account_reference_transaction = "pmb" + userPersonnalInformations_dto.getEmail().substring(0, 1) + userPersonnalInformations_dto.getEmail().substring(2, 5) + userPersonnalInformations_dto.getDisplayName().substring(2, 5) + "b";
		userAccountInformations.setAccountReferenceTransaction(account_reference_transaction);
		userAccountInformations.setUser(userPersonnalInformations_dto);
		return userAccountInformations;
	}
}
