package pmb.pmb.dto;

import java.util.List;

import pmb.pmb.model.UserAccountInformations;

import lombok.Data;
import lombok.Value;
@Data
@Value
public class UserInfo {
	private String id, displayName, email;
	private UserAccountInformationResponse userAccountInformations;
	private List<String> roles;
}