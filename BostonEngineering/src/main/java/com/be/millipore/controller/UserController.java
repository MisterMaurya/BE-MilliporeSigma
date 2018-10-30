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
import com.be.millipore.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/boston/user")
@Api(tags = { APIConstant.USER_CONTROLLER_TAG })
public class UserController {

	@Autowired
	private UserService userService;

// (1). ****** CREATE A NEW USER ****//

	@ApiOperation(value = "Create a New User")
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody User user) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		ResponseEntity<?> responseEntity = userService.validateUser(user);
		if (responseEntity != null) {
			return responseEntity;
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
		ResponseEntity<?> responseEntity = userService.validateUser(user);
		if (responseEntity != null) {
			return responseEntity;
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
		existingUser = userService.findById(id);
		if (existingUser == null) {
			return new ResponseEntity<>(
					jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.USER_NOT_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(existingUser, HttpStatus.OK);
	}

// (4). ****** GET LIST OF ALL USER  ****//

	@ApiOperation(value = "All user list")
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllUser() {
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}

// (5). ****** CHANGE USER STATUS ****//

	@ApiOperation(value = "Change User Status")
	@RequestMapping(value = "/status/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changeUserStatus(@PathVariable Long id) throws JSONException {
		User user = null;
		JSONObject jsonObject = new JSONObject();
		user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<>(
					jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.USER_NOT_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);
		}
		userService.changeStatus(user);
		return new ResponseEntity<>(
				jsonObject.put(APIConstant.STATUS_RESPONSE, APIConstant.USER_STATUS_CHANGE).toString(), HttpStatus.OK);
	}

}
