package pmb.pmb.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import pmb.pmb.config.AppConstants;
import pmb.pmb.dto.AccountSituation;
import pmb.pmb.dto.AddBuddy;
import pmb.pmb.dto.AddCash;
import pmb.pmb.dto.HistoryResponse;
import pmb.pmb.dto.UserBuddy;
import pmb.pmb.dto.UserPartner;
import pmb.pmb.model.HistoryTransaction;
import pmb.pmb.model.Role;
import pmb.pmb.model.User;
import pmb.pmb.model.UserAccountInformations;
import pmb.pmb.model.UserPartnerAccount;
import pmb.pmb.repo.RoleRepository;
import pmb.pmb.repo.UserAccountInfomationsRepository;
import pmb.pmb.repo.UserRepository;

@Service
public class TransactionService implements TransacService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	UserAccountInfomationsRepository userAccountInfomationsRepository;

	/**
	 * @Description add a buddy for transaction
	 */
	@Transactional()
	public String addUserBuddy(AddBuddy buddy) {
		List<User> listUser = userRepository.findAll();
		if (listUser.size() < 0) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("not account situation found");
		}
		for (User u : listUser) {
			UserPartnerAccount userPartnerAccount = new UserPartnerAccount();
			Set<UserPartnerAccount> listUserParterAccount = new HashSet<UserPartnerAccount>();
			if (u.getUserAccountInformations().getAccountReferenceTransaction().contains(buddy.getUserGetter())) {

				userPartnerAccount.setDisplayName(u.getDisplayName());
				userPartnerAccount
						.setUserRefTransaction(u.getUserAccountInformations().getAccountReferenceTransaction());
				userPartnerAccount.setUserAccountInformations(u.getUserAccountInformations());
				listUserParterAccount.add(userPartnerAccount);

			}
			User ux = userRepository.findByUserReferenceTransaction(buddy.getUserSetter());
			if (ux == null) {
				ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("user not found");
			}

			for (UserPartnerAccount us : listUserParterAccount) {
				ux.getUserAccountInformations().getUserPartner_account().add(us);
			}
			userRepository.save(ux);

		}

		return "buddy add  with success";
	}

	/**
	 * 
	 */
	@Transactional
	public String requestTransaction(UserBuddy buddy) {
	System.out.println("@@@@@@@@@@@@@@" + buddy);
		Optional<User> userG = Optional
				.ofNullable(userRepository.findByUserReferenceTransaction(buddy.getUserGetter()));
		
		Optional<User> userS = Optional
				.ofNullable(userRepository.findByUserReferenceTransaction(buddy.getUserSetter()));
		if (userS.get().getUserAccountInformations().getSoldAccount() - buddy.getAmount() < 0) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("you do not have the necessary means");
		} else {
			System.out.println("######################" + userG.get().getUserAccountInformations().getSoldAccount() + "test" + buddy.getAmount());
			userG.get().getUserAccountInformations()
					.setSoldAccount((int) (userG.get().getUserAccountInformations().getSoldAccount() + buddy.getAmount()));
			userS.get().getUserAccountInformations()
					.setSoldAccount((int) (userS.get().getUserAccountInformations().getSoldAccount() - buddy.getAmount()));
			HistoryTransaction historyTransaction = new HistoryTransaction();
			historyTransaction.setDate(LocalDateTime.now());
			historyTransaction.setUser_account_informations(userS.get().getUserAccountInformations());
			historyTransaction.setAccount_reference_transaction(
					userS.get().getUserAccountInformations().getAccountReferenceTransaction());
			userS.get().getUserAccountInformations().getHistoryTransaction().add(historyTransaction);
			userRepository.save(userS.get());
			userRepository.save(userG.get());
		}
		return "transfert with success";
	}

	@Override
	public Set<UserPartner> listUserPartner(long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("not list user found");
		}
		UserPartner userPartner = new UserPartner();
		Set<UserPartner> listUserPartner = new HashSet<UserPartner>();
		for (UserPartnerAccount u : user.get().getUserAccountInformations().getUserPartner_account()) {
			userPartner.setDisplayName(u.getDisplayName());
			userPartner.setUserRefTransaction(u.getUserAccountInformations().getAccountReferenceTransaction());
			listUserPartner.add(userPartner);
		}

		return listUserPartner;
	}

	@Override
	public String addCash(AddCash cash) {
		Optional<User> user = Optional.ofNullable(userRepository.findByUserReferenceTransaction(cash.getUserGetter()));
		if (!user.isPresent()) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("not account situation found");
		} else {
			user.get().getUserAccountInformations().setSoldAccount((int) (user.get().getUserAccountInformations().getSoldAccount() + cash.getAmount()));
			userRepository.save(user.get());
		}
		return "successfully add money";
	}

	@Override
	public Set<HistoryResponse> getListHistory(UserBuddy buddy) {
		Set<HistoryResponse> listHistoryResponse = new HashSet<>();
		Optional<User> user = Optional
				.ofNullable(userRepository.findByUserReferenceTransaction(buddy.getUserSetter()));
		if (!user.isPresent()) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("not user found");
		} else {
			for(HistoryTransaction h: user.get().getUserAccountInformations().getHistoryTransaction()) {
			HistoryResponse hr = new HistoryResponse();
			hr.setDate(h.getDate());
			hr.setAccountReferenceTransaction(user.get().getUserAccountInformations().getAccountReferenceTransaction());
			hr.setSoldAccount(h.getSoldAccount());
			listHistoryResponse.add(hr);
			}
		}
		
		return listHistoryResponse;
	}

	@Override
	public AccountSituation accountSituation(UserBuddy buddy) {
		AccountSituation accountSituation = new AccountSituation();
		Optional<User> user = Optional
				.ofNullable(userRepository.findByUserReferenceTransaction(buddy.getUserSetter()));
		if (!user.isPresent()) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("not user found");
		} else {
	
			accountSituation.setSoldAccount(user.get().getUserAccountInformations().getSoldAccount());
		}
		return accountSituation;
	}
	
	

}
