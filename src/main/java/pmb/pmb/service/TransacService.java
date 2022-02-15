package pmb.pmb.service;

import java.util.Set;

import pmb.pmb.dto.AddBuddy;
import pmb.pmb.dto.UserBuddy;
import pmb.pmb.dto.UserPartner;

public interface TransacService {
	public String addUserBuddy(AddBuddy buddy);
	public Set<UserPartner> listUserPartner(long id);
	public String requestTransaction(UserBuddy buddy);
}
