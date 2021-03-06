package pmb.pmb.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pmb.pmb.config.DtoMapper;
import pmb.pmb.dto.AccountSituation;
import pmb.pmb.dto.AddCash;
import pmb.pmb.dto.HistoryResponse;
import pmb.pmb.dto.Buddy;
import pmb.pmb.dto.UserPartner;
import pmb.pmb.model.HistoryTransaction;
import pmb.pmb.model.User;
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

	@Autowired
	DtoMapper dtoMapper;

	/**
	 * INFORMATIONS ABOUT NAMING METHOD
	 * UserS = userSetter = property of account UserG
	 * UserG = userGetter = buddy partner
	 */

	/**
	 * 
	 * @Description add a buddy for transaction
	 */
	@Transactional()
	public String addUserBuddy(Buddy buddy) {
		UserPartnerAccount userPartner = new UserPartnerAccount();
		if (buddy.getUserGetter() == null) {
			throw new RuntimeException("buddy informations is null");
		}
		String mess = "buddy add  with success";
		User us = userRepository.findByUserReferenceTransaction(buddy.getUserSetter());
		User ug = userRepository.findByUserReferenceTransaction(buddy.getUserGetter());
		if (us == null) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("user not found");
			throw new RuntimeException("user setter not found");
		}
		if (ug == null) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("user not found");
			throw new RuntimeException("user getter not found");
		}
		Set<UserPartnerAccount> lup = new HashSet<>();

		userPartner.setDisplayName(ug.getDisplayName());
		userPartner.setUserRefTransaction(ug.getUserAccountInformations().getAccountReferenceTransaction());
		userPartner.setUserAccountInformations(ug.getUserAccountInformations());

		if (us.getUserAccountInformations().getUserPartner_account() != null) {
			us.getUserAccountInformations().getUserPartner_account().forEach(lup::add);
			lup.add(userPartner);
		}
		us.getUserAccountInformations().setUserPartner_account(lup);
		us.getUserAccountInformations().getUserPartner_account().add(userPartner);
		userRepository.save(us);

		return mess;

	}

	/**
	 * @Description action for start transaction beetween buddy
	 */
	@Transactional
	public String requestTransaction(Buddy buddy) {
		if (buddy.getUserGetter() == null) {
			throw new RuntimeException("buddy informations is null");
		}

		String mess = "transfert with success";
		Optional<User> userG = Optional
				.ofNullable(userRepository.findByUserReferenceTransaction(buddy.getUserGetter()));
		if (!userG.isPresent()) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("no user getter found");
			mess = "no user getter found";
			throw new RuntimeException("no user getter found");
		}

		Optional<User> userS = Optional
				.ofNullable(userRepository.findByUserReferenceTransaction(buddy.getUserSetter()));
		if (!userS.isPresent()) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("no user setter found");
			mess = "no user setter found";
			throw new RuntimeException("no user setter found");
		}

		if (userS.get().getUserAccountInformations().getSoldAccount() - buddy.getAmount() < 0) {
			throw new RuntimeException("you do not have the necessary means");
		} else {
			userG.get().getUserAccountInformations().setSoldAccount(
					(int) (userG.get().getUserAccountInformations().getSoldAccount() + buddy.getAmount()));
			userS.get().getUserAccountInformations().setSoldAccount(
					(int) (userS.get().getUserAccountInformations().getSoldAccount() - buddy.getAmount() - (buddy.getAmount() * 0.5/100)));

			HistoryTransaction historyTransaction = new HistoryTransaction();
			List<HistoryTransaction> lht = new ArrayList<>();

			historyTransaction.setDisplayName(userG.get().getDisplayName());
			historyTransaction.setSoldAccount(userS.get().getUserAccountInformations().getSoldAccount());
			historyTransaction.setDate(LocalDateTime.now());
			historyTransaction.setUser_account_informations(userS.get().getUserAccountInformations());
			historyTransaction.setAccount_reference_transaction(
					userG.get().getUserAccountInformations().getAccountReferenceTransaction());
			historyTransaction.setFee(buddy.getAmount() * 0.5/100);

			lht.add(historyTransaction);
			userS.get().getUserAccountInformations().setHistoryTransaction(lht);
			userRepository.save(userS.get());
			userRepository.save(userG.get());
		}
		return mess;
	}

	/**
	 * @Description get list of partner buddy
	 */
	@Override
	public Set<UserPartner> listUserPartner(long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("not list user found");
			throw new RuntimeException("not list user found");
		}
		UserPartner up = new UserPartner();
		Set<UserPartner> listUserPartner = new HashSet<UserPartner>();
		List<UserPartnerAccount> lip = new ArrayList<>();
		UserPartnerAccount us = new UserPartnerAccount();
		for (UserPartnerAccount upa : user.get().getUserAccountInformations().getUserPartner_account()) {
			lip.add(upa);
		}
		listUserPartner = dtoMapper.map(user.get().getUserAccountInformations().getUserPartner_account());

		return listUserPartner;
	}

	/***
	 * @Description method for add money on this account
	 */
	@Override
	public String addCash(AddCash cash) {
		String mess = "successfully add money";
		Optional<User> user = Optional.ofNullable(userRepository.findByUserReferenceTransaction(cash.getUserGetter()));
		if (!user.isPresent()) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("not account situation found");
			throw new RuntimeException("not account situation found");
		} else {
			user.get().getUserAccountInformations().setSoldAccount(
					(int) (user.get().getUserAccountInformations().getSoldAccount() + cash.getAmount()));
			userRepository.save(user.get());
		}
		return mess;
	}

	/**
	 * @Description get list of historyTransaction
	 */
	@Override
	public List<HistoryResponse> getListHistory(Buddy buddy) {
		if (buddy == null) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("buddy informations is null");
			throw new RuntimeException("buddy informations is null");
		}

		Optional<User> u = Optional.ofNullable(userRepository.findByUserReferenceTransaction(buddy.getUserSetter()));
		if (!u.isPresent()) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("not user found");
			throw new RuntimeException("not user found");
		}
		if (u.get().getUserAccountInformations().getHistoryTransaction().size() < 0) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("not user found");
			throw new RuntimeException("not history list");
		}
		List<HistoryResponse> listHistoryResponse = new ArrayList<>();

		for (HistoryTransaction h : u.get().getUserAccountInformations().getHistoryTransaction()) {
			HistoryResponse hr = new HistoryResponse();
			hr.setDisplayName(h.getDisplayName());
			hr.setDate(h.getDate());
			hr.setSoldAccount(h.getSoldAccount());
			hr.setAccountReferenceTransaction(h.getAccount_reference_transaction());
			hr.setSoldAccount(h.getSoldAccount());
			hr.setFee(h.getFee());
			listHistoryResponse.add(hr);

		}
		return listHistoryResponse;
	}

	/***
	 * @Description get account sold situation
	 */
	@Override
	public AccountSituation accountSituation(Buddy buddy) {
		AccountSituation accountSituation = new AccountSituation();
		Optional<User> user = Optional.ofNullable(userRepository.findByUserReferenceTransaction(buddy.getUserSetter()));
		if (!user.isPresent()) {
			throw new RuntimeException("not user found");
		} else {

			accountSituation.setSoldAccount(user.get().getUserAccountInformations().getSoldAccount());
			accountSituation.setAccountReferenceTransaction(
					user.get().getUserAccountInformations().getAccountReferenceTransaction());
		}
		return accountSituation;
	}

	/**
	 * @Description method for delete a buddy
	 */
	@Override
	public String deleteBuddy(Buddy buddy) {
		if (buddy == null) {
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("buddy informations is null");
			throw new RuntimeException("buddy informations is null");
		}
		
		Optional<User> ug = Optional.ofNullable(userRepository.findByUserReferenceTransaction(buddy.getUserGetter()));
		Optional<User> us = Optional.ofNullable(userRepository.findByUserReferenceTransaction(buddy.getUserSetter()));
		
		if (ug.isPresent()) {
			ug.get().getUserAccountInformations().getUserPartner_account().removeIf(u -> u.getUserAccountInformations()
					.getAccountReferenceTransaction().contains(buddy.getUserSetter()));

			userRepository.save(us.get());

		} else {
			throw new RuntimeException("not user found");
		}
		return "your buddy " + buddy.getUserSetter() + "delete";
	}

}
