package pmb.pmb.controller;

import java.util.ArrayList;
import java.util.List;



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

import pmb.pmb.config.CurrentUser;
import pmb.pmb.dto.JwtUserResponse;
import pmb.pmb.dto.LocalUser;
import pmb.pmb.dto.LoginRequest;
import pmb.pmb.dto.UserBuddy;
import pmb.pmb.dto.UserReferenceTransaction;
import pmb.pmb.model.User;
import pmb.pmb.service.TransactionService;
import pmb.pmb.service.UserService;
import pmb.pmb.util.GeneralUtils;

import net.bytebuddy.asm.Advice.Return;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserService userService;
	


//	@GetMapping("/user/me")
//	@PreAuthorize("hasRole('USER')")
//	public ResponseEntity<?> getCurrentUser( LocalUser user) {
//		System.out.println("###########" + user.getUser());
//		return ResponseEntity.ok(userService.getJwtUserResponseByEmail(user.getAccessTokenHash(), user.getEmail()));
//	}
//	
	@GetMapping("/user/listUserReferenceTransaction")
	@PreAuthorize("hasRole('USER')")
	public List<UserReferenceTransaction>  getListUserReferenceTransaction( ) {
		return userService.listReferenceTransaction();
	}
	

//	
//	@PostMapping("/signin/socialLogin")
//	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//		
//		System.out.println("resultCont2r!!!!!!" + userService.getJwtUserResponseByEmail(jwt, loginRequest.getEmail()));
//		return ResponseEntity.ok(userService.getJwtUserResponseByEmail(jwt, loginRequest.getEmail()));
//	}
	
	
	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
		return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
	}

	@GetMapping("/all")
	public ResponseEntity<?> getContent() {
		return ResponseEntity.ok("Public content goes here");
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserContent() {
		return ResponseEntity.ok("User content goes here");
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAdminContent() {
		return ResponseEntity.ok("Admin content goes here");
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<?> getModeratorContent() {
		return ResponseEntity.ok("Moderator content goes here");
	}
}