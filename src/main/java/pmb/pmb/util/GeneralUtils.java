package pmb.pmb.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import pmb.pmb.dto.LocalUser;
import pmb.pmb.dto.SocialProvider;
import pmb.pmb.dto.UserInfo;
import pmb.pmb.model.Role;
import pmb.pmb.model.User;
import pmb.pmb.model.UserAccountInformations;
import pmb.pmb.repo.UserRepository;
import pmb.pmb.service.UserService;

/**
 * @Description method for add role and social provider in user 
 * @author j.de-la-osa
 *
 */
public class GeneralUtils {
	
	@Autowired
	private static UserService userService;

	public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	public static SocialProvider toSocialProvider(String providerId) {
		for (SocialProvider socialProvider : SocialProvider.values()) {
			if (socialProvider.getProviderType().equals(providerId)) {
				return socialProvider;
			}
		}
		return SocialProvider.LOCAL;
	}

	public static UserInfo buildUserInfo(LocalUser localUser) {
		List<String> roles = localUser.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		User user = localUser.getUser();
		
		
		return new UserInfo(user.getId().toString(), user.getDisplayName(), user.getEmail(), user.getUserAccountInformations().toDTO(), roles);
	}
}