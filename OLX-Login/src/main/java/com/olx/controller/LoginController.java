package com.olx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.AuthenticationRequest;
import com.olx.dto.User;
import com.olx.entity.BlacklistTokenDocument;
import com.olx.repository.BlacklistTokenRepository;
import com.olx.security.JwtUtil;
import com.olx.service.LoginService;

@RestController
@RequestMapping("olx/user")
public class LoginController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	LoginService loginService;

	@Autowired
	BlacklistTokenRepository tokenRepository;

	@Autowired
	JwtUtil jwtUtil;

	@PostMapping(value = "/authenticate", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> userLogin(@RequestBody AuthenticationRequest request) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException exception) {
			return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
		String authToken = jwtUtil.generateTokenByUsername(request.getUsername());
		return new ResponseEntity<String>(authToken, HttpStatus.OK);
	}

	@PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		User userDto = loginService.createNewUser(user);
		return new ResponseEntity<User>(userDto, HttpStatus.CREATED);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<User> getUserById(@PathVariable int id) {
		User user = loginService.getUserById(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping(value = "/validate/token", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Boolean> validateUserToken(@RequestHeader("Authorization") String token) {

		String authToken = token.substring(token.indexOf(' ') + 1);
		String clientUserName = jwtUtil.extractUsername(authToken);
		String dbUserName = userDetailsService.loadUserByUsername(clientUserName).getUsername();
		boolean isTokenValid = jwtUtil.validateTokenByUserName(authToken, dbUserName);
		if (isTokenValid) {
			BlacklistTokenDocument dbToken = tokenRepository.findByTokenEquals(authToken);
			if (dbToken != null) {
				return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/username", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<String> getUserName(@RequestParam("auth-token") String authToken) {
		String username = loginService.getUserName(authToken);
		return new ResponseEntity<String>(username, HttpStatus.OK);
	}

	@DeleteMapping(value = "/logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String authToken) {
		String token = authToken.substring(authToken.indexOf(' ') + 1);
		BlacklistTokenDocument tokenDocument = new BlacklistTokenDocument();
		tokenDocument.setToken(token);
		tokenRepository.save(tokenDocument);
		return new ResponseEntity<String>("Successfully Logged Out !", HttpStatus.OK);
	}
}
