package pmb.pmb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pmb.pmb.dto.UserBuddy;
import pmb.pmb.service.TransactionService;

@RestController
@RequestMapping("/api")
public class TransactionController {

	@Autowired
	TransactionService transactionService;
	
	@PostMapping("/user/addBuddy")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> addBuddyForTransaction(@RequestBody UserBuddy userGetter ) {
		return ResponseEntity.ok(transactionService.addUserBuddy(userGetter));
	}
	
	@PostMapping("/user/transactionAccount")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> startTransaction(@RequestBody UserBuddy userGetter, double amount ) {
		return ResponseEntity.ok(transactionService.requestTransaction(userGetter, amount));
	}
}
