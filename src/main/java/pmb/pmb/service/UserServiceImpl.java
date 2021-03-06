package pmb.pmb.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import pmb.pmb.config.ExcludeFromJacocoGeneratedReport;
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
import pmb.pmb.repo.UserRepository;
import pmb.pmb.security.oauth2.user.OAuth2UserInfo;
import pmb.pmb.security.oauth2.user.OAuth2UserInfoFactory;
import pmb.pmb.util.GeneralUtils;

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

	/**
	 * method for add new user
	 */
	@Override
	@Transactional(value = "transactionManager")
	@ExcludeFromJacocoGeneratedReport
	public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
		if (signUpRequest == null) {
			throw new RuntimeException("this informations for signup is null");
		}
		if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
			throw new UserAlreadyExistAuthenticationException(
					"User with User id " + signUpRequest.getUserID() + " already exist");
		} else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new UserAlreadyExistAuthenticationException(
					"User with email id " + signUpRequest.getEmail() + " already exist");
		}
		User user = buildUser(signUpRequest);
		Date now = Calendar.getInstance().getTime();
		user.setUserAccountInformations(userAccountRegistrationService.attributeAccountInformations(user));
		userRepository.flush();
		return user;
	}

	/**
	 * @Description private method for build user object
	 */
	@ExcludeFromJacocoGeneratedReport
	private User buildUser(final SignUpRequest formDTO) {
		if (formDTO == null) {
			throw new RuntimeException("this informations for signup is null");
		}
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

	/***
	 * @Description method for get user with email
	 */
	@Override
	@ExcludeFromJacocoGeneratedReport
	public User findUserByEmail(String email) {
		if (email == null) {
			throw new RuntimeException("Error: email is null");
		}
		return userRepository.findByEmail(email);
	}

	/**
	 * @Description method for add new user with social login
	 */
	@Override
	@Transactional
	@ExcludeFromJacocoGeneratedReport
	public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken,
			OidcUserInfo userInfo) {

		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
		if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
			throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
		} else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}
		SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
		User user = findUserByEmail(oAuth2UserInfo.getEmail());
		if (user != null) {
			if (!user.getProvider().equals(registrationId)
					&& !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your "
								+ user.getProvider() + " account to login.");
			}
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			user = registerNewUser(userDetails);
		}

		return LocalUser.create(user, attributes, idToken, userInfo);
	}

	/**
	 * @Description method for update user social login
	 * @param existingUser
	 * @param oAuth2UserInfo
	 * @return
	 */
	@ExcludeFromJacocoGeneratedReport
	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		if (existingUser == null || oAuth2UserInfo == null) {
			throw new RuntimeException("informatios from update user is not found");
		}
		existingUser.setDisplayName(oAuth2UserInfo.getName());
		return userRepository.save(existingUser);
	}

	@ExcludeFromJacocoGeneratedReport
	private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
		return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId())
				.addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
				.addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
	}

	/**
	 * @Description get user by id
	 */
	@Override
	public Optional<User> findUserById(Long id) {
		return Optional.ofNullable(userRepository.findUserById(id));
	}

	/**
	 * @Description method for get jwt informations with email connexion method
	 */
	@Override
	public JwtUserResponse getJwtUserResponseByEmail(String jwt, String email) {
		if (email == null) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email client is null");
			throw new RuntimeException("email is null");
		}

		Optional<User> user = Optional.ofNullable(userRepository.foundByEmail(email));

		if (!user.isPresent()) {
			ResponseEntity.status(HttpStatus.NO_CONTENT).body("not user found");
		}

		List<String> roles = new ArrayList<String>();
		UserAccountInformations userAccountInformations = new UserAccountInformations();

		roles.add(Role.ROLE_USER);

		userAccountInformations.setSoldAccount(user.get().getUserAccountInformations().getSoldAccount());
		userAccountInformations.setAccountReferenceTransaction(
				user.get().getUserAccountInformations().getAccountReferenceTransaction());
		JwtUserResponse jwtUserResponse = new JwtUserResponse(jwt, user.get().getId(), user.get().getEmail(),
				user.get().getDisplayName(), roles, userAccountInformations);
		return jwtUserResponse;
	}

	/**
	 * @Description het list of accountReferenceTransaction
	 */
	@Override
	public ArrayList<UserReferenceTransaction> listReferenceTransaction() {
		ArrayList<UserReferenceTransaction> listUserReferenceTransactions = new ArrayList<>();
		List<User> listUser = userRepository.findAll();

		if (listUser.isEmpty()) {
			ResponseEntity.status(HttpStatus.NO_CONTENT).body("not list user found");
		}

		for (User u : listUser) {
			UserReferenceTransaction userReferenceTransaction = new UserReferenceTransaction();
			userReferenceTransaction
					.setAccountReferenceTransaction(u.getUserAccountInformations().getAccountReferenceTransaction());
			userReferenceTransaction.setDisplayName(u.getDisplayName());

			if (!listUserReferenceTransactions.contains(userReferenceTransaction)) {
				listUserReferenceTransactions.add(userReferenceTransaction);
			}
		}

		if (listUserReferenceTransactions.isEmpty()) {
			ResponseEntity.status(HttpStatus.NO_CONTENT).body("not list user found");

		}
		return listUserReferenceTransactions;
	}

	/**
	 * @Description method for get user with this id
	 */
	@Override
	public User getUserById(long id) {
		Optional<User> u = Optional.ofNullable(userRepository.findUserById(id));

		if (!u.isPresent()) {
			throw new RuntimeException("user not found");
		}
		return u.get();
	}

	/**
	 * @Description method for get user with this email
	 */
	@Override
	@ExcludeFromJacocoGeneratedReport
	public JwtUserResponse getUserByEmail(String email) {
		Optional<User> u = Optional.ofNullable(userRepository.findByEmail(email));
		JwtUserResponse jwtUserResponse = new JwtUserResponse(null, u.get().getId(), u.get().getEmail(),
				u.get().getDisplayName(), null, u.get().getUserAccountInformations());
		return jwtUserResponse;
	}

	@Override
	@ExcludeFromJacocoGeneratedReport
	public UserAccountInformations getUserInfo(long id) {
		Optional<User> u = userRepository.findById(id);
		UserAccountInformations userAccountInformations = new UserAccountInformations();
		userAccountInformations
				.setAccountReferenceTransaction(u.get().getUserAccountInformations().getAccountReferenceTransaction());
		userAccountInformations.setSoldAccount(u.get().getUserAccountInformations().getSoldAccount());
		if (userAccountInformations != null) {
			throw new RuntimeException("Null informations of account");
		}
		return userAccountInformations;
	}

	/**
	 * @Description method for annotation of exist test mockito
	 * @param annotation
	 * @return
	 */
	@ExcludeFromJacocoGeneratedReport
	private static boolean matches(final String annotation) {
		final String name = annotation
				.substring(Math.max(annotation.lastIndexOf('/'), annotation.lastIndexOf('$')) + 1);
		return name.contains("Generated");
	}

}