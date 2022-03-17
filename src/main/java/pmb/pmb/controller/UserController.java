package pmb.pmb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import pmb.pmb.config.CurrentUser;
import pmb.pmb.config.ExcludeFromJacocoGeneratedReport;
import pmb.pmb.dto.JwtUserResponse;
import pmb.pmb.dto.LocalUser;
import pmb.pmb.dto.LoginRequest;
import pmb.pmb.dto.Buddy;
import pmb.pmb.dto.UserReferenceTransaction;
import pmb.pmb.model.User;
import pmb.pmb.repo.UserRepository;
import pmb.pmb.service.TransactionService;
import pmb.pmb.service.UserService;
import pmb.pmb.util.GeneralUtils;

import net.bytebuddy.asm.Advice.Return;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("/user/listUserReferenceTransaction")
	@PreAuthorize("hasRole('USER')")
	public List<UserReferenceTransaction>  getListUserReferenceTransaction( ) {
		return userService.listReferenceTransaction();
	}
	
	
	@PostMapping("/user/getUserInformations")
	//@PreAuthorize("hasRole('USER')")
	public JwtUserResponse  getUserInformations(@RequestBody String email) {
		System.out.println("]##@@###############" + email);
		return userService.getUserByEmail(email);
	}
	
	
	/**
	 * @Description controller for get informations when user is connect with social login
	 * @param user
	 * @return
	 */
	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	@ExcludeFromJacocoGeneratedReport
	public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
	
	return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
	}


	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserContent() {
		return ResponseEntity.ok("User content goes here");
	}
}