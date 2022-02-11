package pmb.pmb.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "userAccountInformations")
public class UserAccountInformations {
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAccountReferenceTransaction() {
		return accountReferenceTransaction;
	}

	public void setAccountReferenceTransaction(String accountReferenceTransaction) {
		this.accountReferenceTransaction = accountReferenceTransaction;
	}

	public int getNumberAccount() {
		return numberAccount;
	}

	public void setNumberAccount(int numberAccount) {
		this.numberAccount = numberAccount;
	}

	public int getSoldAccount() {
		return soldAccount;
	}

	public void setSoldAccount(int soldAccount) {
		this.soldAccount = soldAccount;
	}

	public UserPartnerAccount getUserPartner_account() {
		return userPartner_account;
	}

	public void setUserPartner_account(UserPartnerAccount userPartner_account) {
		this.userPartner_account = userPartner_account;
	}

	public HistoryTransaction getHistoryTransaction() {
		return historyTransaction;
	}

	public void setHistoryTransaction(HistoryTransaction historyTransaction) {
		this.historyTransaction = historyTransaction;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserAccountInformations_ID")
	long id;
	
	@OneToOne
	User user;
	
	String accountReferenceTransaction;
	
	int numberAccount;
	
	int soldAccount;
	
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	UserPartnerAccount userPartner_account;
	
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	HistoryTransaction historyTransaction;
	
	boolean state;
	
	
}