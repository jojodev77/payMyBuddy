package pmb.pmb.service;

import java.util.List;
import java.util.Set;

import pmb.pmb.dto.AccountSituation;
import pmb.pmb.dto.AddBuddy;
import pmb.pmb.dto.AddCash;
import pmb.pmb.dto.HistoryResponse;
import pmb.pmb.dto.UserBuddy;
import pmb.pmb.dto.UserPartner;

public interface TransacService {
	public String addUserBuddy(AddBuddy buddy);
	public String deleteBuddy(UserBuddy buddy);
	public Set<UserPartner> listUserPartner(long id);
	public String requestTransaction(UserBuddy buddy);
	public String addCash(AddCash cash);
	public List<HistoryResponse> getListHistory(UserBuddy buddy);
	public AccountSituation accountSituation(UserBuddy buddy);
}
