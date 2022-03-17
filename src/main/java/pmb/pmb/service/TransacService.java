package pmb.pmb.service;

import java.util.List;
import java.util.Set;

import pmb.pmb.dto.AccountSituation;
import pmb.pmb.dto.AddCash;
import pmb.pmb.dto.HistoryResponse;
import pmb.pmb.dto.Buddy;
import pmb.pmb.dto.UserPartner;

public interface TransacService {
	public String addUserBuddy(Buddy buddy);
	public String deleteBuddy(Buddy buddy);
	public Set<UserPartner> listUserPartner(long id);
	public String requestTransaction(Buddy buddy);
	public String addCash(AddCash cash);
	public List<HistoryResponse> getListHistory(Buddy buddy);
	public AccountSituation accountSituation(Buddy buddy);
}
