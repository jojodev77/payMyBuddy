package pmb.pmb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pmb.pmb.config.ExcludeFromJacocoGeneratedReport;
import pmb.pmb.dto.LocalUser;
import pmb.pmb.exception.ResourceNotFoundException;
import pmb.pmb.model.User;
import pmb.pmb.util.GeneralUtils;


@Service("localUserDetailService")
public class LocalUserDetailService implements UserDetailsService {

	@Autowired
	private UserService userService;

	/**
	 * @Description method for get user with this email
	 */
	@Override
	@Transactional
	public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
		User user = userService.findUserByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User " + email + " was not found in the database");
		}
		return createLocalUser(user);
	}

	
	/**
	 * @Description method for get user with this id
	 */
	@Transactional
	@ExcludeFromJacocoGeneratedReport
	public LocalUser loadUserById(Long id) {
		User user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return createLocalUser(user);
	}

	/**
	 * @param user
	 * @return
	 * @Description method for build user object with social login create user
	 */
	@ExcludeFromJacocoGeneratedReport
	private LocalUser createLocalUser(User user) {
		return new LocalUser(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()), user);
	}
}
