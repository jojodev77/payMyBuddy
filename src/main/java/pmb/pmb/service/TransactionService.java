package pmb.pmb.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pmb.pmb.config.AppConstants;
import pmb.pmb.dto.UserBuddy;
import pmb.pmb.model.HistoryTransaction;
import pmb.pmb.model.User;
import pmb.pmb.repo.UserRepository;

@Service
public class TransactionService {

	@Autowired
	UserRepository userRepository;

	public String addUserBuddy(UserBuddy userGetter) {
		User us = new User();
		Optional<User> userG = userRepository.findByUserReferenceTransaction(
				userGetter.getUserGetter().getUserAccountInformations().getAccountReferenceTransaction());
		Optional<User> userS = userRepository.findByUserReferenceTransaction(
				userGetter.getUserSetter().getUserAccountInformations().getAccountReferenceTransaction());
		if (userS.get().getUserAccountInformations().getUserPartner_account().getUserRefTransaction() == null) {
			us.getUserAccountInformations()
					.setUserPartner_account(userG.get().getUserAccountInformations().getUserPartner_account());
		} else {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("this buddy exist ");
		}
		return "buddy add with success";
	}

	@Transactional()
	public String requestTransaction(UserBuddy userBuddy, double amount) {
		double finalyAmount = amount + AppConstants.TAXATION_TRANSACTION / 100;
		Optional<User> userG = userRepository.findById(userBuddy.getUserGetter().getId());
		Optional<User> userS = userRepository.findById(userBuddy.getUserSetter().getId());
		if (userG.get().getUserAccountInformations().getSoldAccount() - finalyAmount < 0) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("you do not have the necessary means");
		} else {
			userG.get().getUserAccountInformations()
					.setSoldAccount((int) (userG.get().getUserAccountInformations().getSoldAccount() - finalyAmount));
			userS.get().getUserAccountInformations()
					.setSoldAccount((int) (userS.get().getUserAccountInformations().getSoldAccount() + finalyAmount));
			HistoryTransaction historyTransaction = new HistoryTransaction();
			historyTransaction.setDate(LocalDateTime.now());
			historyTransaction.setAccount_reference_transaction(
					userS.get().getUserAccountInformations().getAccountReferenceTransaction());
		}
		return "transfert with success";
	}

}
