package pmb.pmb.dto;

import javax.persistence.OneToOne;

import lombok.Data;
import pmb.pmb.model.User;

@Data
public class UserAccountInformationResponse {
	long id;

	String accountReferenceTransaction;

	int numberAccount;

	int soldAccount;
}
