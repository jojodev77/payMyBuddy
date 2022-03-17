package pmb.pmb.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pmb.pmb.dto.AccountSituation;
import pmb.pmb.dto.AddCash;
import pmb.pmb.dto.Buddy;
import pmb.pmb.dto.HistoryResponse;
import pmb.pmb.dto.UserPartner;
import pmb.pmb.service.TransacService;

@RestController
@RequestMapping("/api")
public class TransactionController {

	@Autowired
	TransacService transactionService;
	
	@PostMapping("/user/addBuddy")
	//@PreAuthorize("hasRole('USER')")
	public String addBuddyForTransaction( @RequestBody Buddy buddy ) {
		return transactionService.addUserBuddy(buddy);
	}
	
	@PostMapping("/user/deleteBuddy")
	//@PreAuthorize("hasRole('USER')")
	public String deleteBuddyForTransaction( @RequestBody Buddy buddy ) {
		return transactionService.deleteBuddy(buddy);
	}
	
	@PostMapping("/user/history")
//	@PreAuthorize("hasRole('USER')")
	public List<HistoryResponse> getHistory( @RequestBody Buddy buddy ) {
		return transactionService.getListHistory(buddy);
	}
	
	@PostMapping("/user/startTransaction")
//	@PreAuthorize("hasRole('USER')")
	public String startTransaction( @RequestBody Buddy userGetter ) {
		return transactionService.requestTransaction(userGetter);
	}
	
	@PostMapping("/user/accountSituation")
//	@PreAuthorize("hasRole('USER')")
	public AccountSituation accountSituation( @RequestBody Buddy buddy ) {
		return transactionService.accountSituation(buddy);
	}
	
	@PostMapping("/user/getListBuddy")
	@PreAuthorize("hasRole('USER')")
	public Set<UserPartner> getListBuddy( @RequestBody long id ) {
		return transactionService.listUserPartner(id);
	}
	
	@PostMapping("/user/addCash")
//	@PreAuthorize("hasRole('USER')")
	public String addCash( @RequestBody AddCash cash ) {
		return transactionService.addCash(cash);
	}
}
