package pmb.pmb.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pmb.pmb.dto.AddBuddy;
import pmb.pmb.dto.UserBuddy;
import pmb.pmb.dto.UserPartner;
import pmb.pmb.service.TransacService;
import pmb.pmb.service.TransactionService;

@RestController
@RequestMapping("/api")
public class TransactionController {

	@Autowired
	TransacService transactionService;
	
	@PostMapping("/user/addBuddy")
	//@PreAuthorize("hasRole('USER')")
	public String addBuddyForTransaction( @RequestBody AddBuddy buddy ) {
		return transactionService.addUserBuddy(buddy);
	}
	
	@PostMapping("/user/transactionAccount")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> startTransaction(@RequestBody UserBuddy userGetter, double amount ) {
		return ResponseEntity.ok(transactionService.requestTransaction(userGetter, amount));
	}
	
	@PostMapping("/user/getListBuddy")
	@PreAuthorize("hasRole('USER')")
	public Set<UserPartner> getListBuddy( @RequestBody long id ) {
		return transactionService.listUserPartner(id);
	}
}
