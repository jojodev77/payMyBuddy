package pmb.pmb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import pmb.pmb.dto.JwtUserResponse;
import pmb.pmb.dto.LocalUser;
import pmb.pmb.dto.SignUpRequest;
import pmb.pmb.dto.UserReferenceTransaction;
import pmb.pmb.exception.UserAlreadyExistAuthenticationException;
import pmb.pmb.model.User;
import pmb.pmb.model.UserAccountInformations;


public interface UserService {

	public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

	User findUserByEmail(String email);

	Optional<User> findUserById(Long id);

	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);

	public JwtUserResponse getJwtUserResponseByEmail(String jwt, String email);
	
	List<UserReferenceTransaction> listReferenceTransaction();
	
	User getUserById(long id);
	
	JwtUserResponse getUserByEmail(String email);
	
	UserAccountInformations getUserInfo(long id);
}
