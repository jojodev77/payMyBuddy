package pmb.pmb.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import pmb.pmb.dto.JwtUserResponse;
import pmb.pmb.dto.LocalUser;
import pmb.pmb.dto.SignUpRequest;
import pmb.pmb.dto.SocialProvider;
import pmb.pmb.dto.UserReferenceTransaction;
import pmb.pmb.exception.OAuth2AuthenticationProcessingException;
import pmb.pmb.exception.UserAlreadyExistAuthenticationException;
import pmb.pmb.model.Role;
import pmb.pmb.model.User;
import pmb.pmb.model.UserAccountInformations;
import pmb.pmb.repo.RoleRepository;
import pmb.pmb.repo.UserAccountInfomationsRepository;
import pmb.pmb.repo.UserRepository;
import pmb.pmb.security.oauth2.user.OAuth2UserInfo;
import pmb.pmb.security.oauth2.user.OAuth2UserInfoFactory;
import pmb.pmb.util.GeneralUtils;

/**
 * @author Chinna
 * @since 26/3/18
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserAccountRegistrationService userAccountRegistrationService;
	
	@Autowired
	private UserAccountInfomationsRepository userAccountInfomationsRepository;

	@Override
	@Transactional(value = "transactionManager")
	public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
		if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
			throw new UserAlreadyExistAuthenticationException("User with User id " + signUpRequest.getUserID() + " already exist");
		} else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
		}
		User user = buildUser(signUpRequest);
		Date now = Calendar.getInstance().getTime();
		user.setUserAccountInformations(userAccountRegistrationService.attributeAccountInformations(user));
		//user.getUserAccountInformations().setUser(user);
		userRepository.flush();
		return user;
	}

	private User buildUser(final SignUpRequest formDTO) {
		User user = new User();
		user.setDisplayName(formDTO.getDisplayName());
		user.setEmail(formDTO.getEmail());
		user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
		final HashSet<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName(Role.ROLE_USER));
		user.setRoles(roles);
		user.setProvider(formDTO.getSocialProvider().getProviderType());
		user.setEnabled(true);
		user = userRepository.save(user);
		return user;
	}

	@Override
	public User findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}
	



	@Override
	@Transactional
	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
		if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
			throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
		} else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}
		SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
		User user = findUserByEmail(oAuth2UserInfo.getEmail());
		if (user != null) {
			if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
			}
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			user = registerNewUser(userDetails);
		}

		return LocalUser.create(user, attributes, idToken, userInfo);
	}

	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setDisplayName(oAuth2UserInfo.getName());
		return userRepository.save(existingUser);
	}

	private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
		return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
				.addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
	}

	@Override
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public JwtUserResponse getJwtUserResponseByEmail(String jwt, String email) {
		if (email == null) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email client is null");
		}
		Optional<User> user = Optional.ofNullable(userRepository.foundByEmail(email));
		if (!user.isPresent()) {
			ResponseEntity.status(HttpStatus.NO_CONTENT).body("not user found");
		}
		List<String> roles = new ArrayList<String>();
		roles.add(Role.ROLE_USER);
	UserAccountInformations userAccountInformations = new UserAccountInformations();
	userAccountInformations.setSoldAccount(user.get().getUserAccountInformations().getSoldAccount());
	userAccountInformations.setAccountReferenceTransaction(user.get().getUserAccountInformations().getAccountReferenceTransaction());
		JwtUserResponse jwtUserResponse = new JwtUserResponse(jwt,user.get().getId(),  user.get().getEmail(),user.get().getDisplayName(),roles, userAccountInformations);
		return jwtUserResponse;
	}

	@Override
	public ArrayList<UserReferenceTransaction> listReferenceTransaction() {
		UserReferenceTransaction userReferenceTransaction = new UserReferenceTransaction();
		ArrayList<UserReferenceTransaction> listUserReferenceTransactions = new ArrayList<>();
		List<User> listUser = userRepository.findAll();
		if (listUser.isEmpty()) {
			ResponseEntity.status(HttpStatus.NO_CONTENT).body("not user found");
		}


			
			for (int i = 0; i < listUser.size(); i++) {
				userReferenceTransaction.setAccountReferenceTransaction(listUser.get(i).getUserAccountInformations().getAccountReferenceTransaction());
				userReferenceTransaction.setDisplayName(listUser.get(i).getDisplayName());
				if (userReferenceTransaction != null) {
					listUserReferenceTransactions.add(userReferenceTransaction);
				}
				 
			 //   System.out.println(listUser.get(i));
		
			}
		   
		    System.out.println("%%%%%%%%%%%%%%%%%%%%" + listUserReferenceTransactions);
	
		if (listUserReferenceTransactions.isEmpty()) {
			ResponseEntity.status(HttpStatus.NO_CONTENT).body("not list user found");
			
		}
		return listUserReferenceTransactions;
	}


}