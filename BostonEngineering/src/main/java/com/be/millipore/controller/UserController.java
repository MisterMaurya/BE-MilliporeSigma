package com.be.millipore.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.be.millipore.apiconstant.APIConstant;
import com.be.millipore.beans.User;
import com.be.millipore.beans.UserRole;
import com.be.millipore.repository.UserRoleRepo;
import com.be.millipore.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/boston/user")
@Api(description = "OPERATIONS PERTAINING TO BOSTON USER")
//@Api(tags = { "tag1" })
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleRepo userRoleRepo;

// (1). ****** CREATE A NEW USER ****//

	@ApiOperation(value = "Create a New User")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody User user) throws JSONException {
		User existingUser = null;
		JSONObject jsonObject = new JSONObject();
		existingUser = userService.findByEmail(user.getEmail());

		if (existingUser != null) {
			return new ResponseEntity<String>(
					jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.EMAIL_ALREADY_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);

		}

		existingUser = userService.findByMobile(user.getMobile());

		if (existingUser != null) {

			return new ResponseEntity<String>(
					jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.MOBILE_NO_ALREADY_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);
		}

		existingUser = userService.findByUserId(user.getUserId());

		if (existingUser != null) {
			return new ResponseEntity<>(
					jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.USERID_ALREADY_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);

		}

		for (UserRole i : user.getRole()) {
			if (userRoleRepo.findById(i.getUserRoleId()).isPresent()) {
				if (i.getUserRole().equals("Operator")) {
					existingUser = userService.getLineManager(user.getLineManageId());
					if (existingUser == null) {
						return new ResponseEntity<>(jsonObject
								.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.LINE_MANAGER_IS_STATUS).toString(),
								HttpStatus.OK);
					}
				}
			} else {
				return new ResponseEntity<>(
						jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.USER_ROLE_NOT_EXISTS).toString(),
						HttpStatus.OK);
			}
		}

		user.setStatus("Y");

		userService.save(user);

		return new ResponseEntity<>(
				jsonObject.put(APIConstant.USER_CREATE_STATUS, APIConstant.USER_SUCCESSFULLY_CREATED).toString(),
				HttpStatus.OK);

	}

// (2). ****** UPDATE A USER ****//	

	@ApiOperation(value = "Update a user")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		User existingUser = null;
		existingUser = userService.getUser(id);

		if (existingUser == null) {
			return new ResponseEntity<>(
					jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.USER_NOT_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);
		}

		userService.save(user);
		return new ResponseEntity<>(
				jsonObject.put(APIConstant.UPDATE_STATUS, APIConstant.USER_SUCCESSFULLY_UPDATED).toString(),
				HttpStatus.OK);
	}

// (3). ****** GET ONE USER ****//

	@ApiOperation(value = "Get A User Details")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUser(@PathVariable Long id) throws JSONException {
		User existingUser = null;
		JSONObject jsonObject = new JSONObject();
		existingUser = userService.getUser(id);
		if (existingUser == null) {

			return new ResponseEntity<>(
					jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.USER_NOT_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(existingUser, HttpStatus.OK);
	}

// (4). ****** GET LIST ALL USER  ****//

	@ApiOperation(value = "All user list")
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllUser() {

		return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);

	}

// (5). ****** DELETE A USER ****//

	@ApiOperation(value = "Delete a user")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteUser(@PathVariable Long id) throws JSONException {
		User user = null;
		JSONObject jsonObject = new JSONObject();
		user = userService.getUser(id);
		if (user == null) {
			return new ResponseEntity<>(
					jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.USER_NOT_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);
		}
		userService.deleteUser(id, user);
		return new ResponseEntity<>(
				jsonObject.put(APIConstant.USER_SUCCESSFULLY_DELETED, APIConstant.USER_DELETE_RESPONSE).toString(),
				HttpStatus.OK);
	}

}
