package com.be.millipore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.be.millipore.beans.LoginUser;
import com.be.millipore.constant.APIConstant;
import com.be.millipore.security.AuthToken;
import com.be.millipore.security.TokenProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/login")
@Api(tags = { APIConstant.USER_LOGIN_TAG })
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider jwtTokenUtil;

// (1). ****** LOGIN API ****//	

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = APIConstant.USER_LOGIN)
	public ResponseEntity<?> login(@RequestBody LoginUser loginUser) throws AuthenticationException {

		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		final String token = jwtTokenUtil.generateToken(authentication);
		return ResponseEntity.ok(new AuthToken(token));
	}

// (2). ****** LOGIN JSP ****//	

	@RequestMapping(method = RequestMethod.GET)
	public String indexPage(ModelMap model) {
		return "login";
	}

}