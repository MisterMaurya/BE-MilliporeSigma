package com.be.millipore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.be.millipore.beans.UserRole;
import com.be.millipore.repository.UserRoleRepo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/boston/user")
@Api(description = "Operations pertaining to boston user role")
public class UserRoleController {
	@Autowired
	private UserRoleRepo userRoleRepo;

	// Add Role for user

	@ApiOperation(value = "Insert Role")
	@RequestMapping(value = "/user/role", method = RequestMethod.POST)
	public ResponseEntity<?> saveUserRole(@RequestBody UserRole userRole, BindingResult res) {

		userRoleRepo.save(userRole);
		return new ResponseEntity<>("Successfully role add", HttpStatus.OK);

	}

	// Get All Role List

	@ApiOperation(value = "Get All Role")
	@RequestMapping(value = "/get/role/list", method = RequestMethod.GET, produces = "application/json")
	public List<UserRole> getUserRoleList() {
		return (List<UserRole>) userRoleRepo.findAll();
	}

}
