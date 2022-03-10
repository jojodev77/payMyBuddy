package pmb.pmb.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_partner_account")
public class UserPartnerAccount implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@ManyToOne
	UserAccountInformations user_account_informations;
	
	String userRefTransaction;
	
	String displayName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserAccountInformations getUserAccountInformations() {
		return user_account_informations;
	}

	public void setUserAccountInformations(UserAccountInformations user_account_informations) {
		this.user_account_informations = user_account_informations;
	}

	public String getUserRefTransaction() {
		return userRefTransaction;
	}

	public void setUserRefTransaction(String userRefTransaction) {
		this.userRefTransaction = userRefTransaction;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
