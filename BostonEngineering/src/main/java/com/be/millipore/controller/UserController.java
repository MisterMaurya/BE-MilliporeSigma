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
import com.be.millipore.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = APIConstant.REST_BASE_URL + APIConstant.REST_USER_URL)
@Api(tags = { APIConstant.USER_CONTROLLER_TAG })
public class UserController {

	@Autowired
	private UserService userService;

// (1). ****** CREATE A NEW USER ****//

	@ApiOperation(value = APIConstant.USER_CREATE)
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody User user) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		ResponseEntity<?> responseEntity = userService.validateUser(user);
		if (responseEntity != null) {
			return responseEntity;
		}
		userService.save(user);
		return new ResponseEntity<>(
				jsonObject.put(APIConstant.USER_CREATE_STATUS, APIConstant.USER_SUCCESSFULLY_CREATED).toString(),
				HttpStatus.OK);

	}

// (2). ****** UPDATE A USER ****//	

	@ApiOperation(value = APIConstant.USER_UPDATE)
	@RequestMapping(value = APIConstant.REST_ID_PARAM, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		ResponseEntity<?> responseEntity = userService.validateUser(user);
		if (responseEntity != null) {
			return responseEntity;
		}
		userService.save(user);
		return new ResponseEntity<>(
				jsonObject.put(APIConstant.USER_UPDATE_STATUS, APIConstant.USER_SUCCESSFULLY_UPDATED).toString(),
				HttpStatus.OK);
	}

// (3). ****** GET ONE USER ****//

	@ApiOperation(value = APIConstant.GET_ONE_USER)
	@RequestMapping(value = APIConstant.REST_ID_PARAM, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUser(@PathVariable Long id) throws JSONException {
		User existingUser = null;
		JSONObject jsonObject = new JSONObject();
		existingUser = userService.findById(id);
		if (existingUser == null) {

			jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.USER_NOT_EXISTS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}
		jsonObject = userService.getOneUser(id);
		for (UserRole userRole : existingUser.getRole()) {
			if (userRole.getUserRole().equals("Operator")) {
				User getManagerDetails = userService.findById(existingUser.getLineManageId());
				jsonObject.put("Manager Name", getManagerDetails.getFullName());
				jsonObject.put("Manager Email", getManagerDetails.getEmail());
				jsonObject.put("Manager Mobile", getManagerDetails.getMobile());

			}
		}

		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

// (4). ****** GET LIST OF ALL USER  ****//

	@ApiOperation(value = APIConstant.GET_ALL_USER)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllUser() {
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}

// (5). ****** CHANGE USER STATUS ****//

	@ApiOperation(value = APIConstant.CHANGE_STATUS)
	@RequestMapping(value = APIConstant.REST_ID_STATUS
			+ APIConstant.REST_ID_PARAM, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changeUserStatus(@PathVariable Long id) throws JSONException {
		User user = null;
		JSONObject jsonObject = new JSONObject();
		user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<>(
					jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.USER_NOT_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);
		}

		return userService.changeStatus(user);
	}

// (6). GET ALL ACTIVE LINE MANAGER

	@ApiOperation(value = APIConstant.GET_ALL_ACIVE_MANAGER)
	@RequestMapping(value = APIConstant.ALL_ACTIVE_MANAGER, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllActiveManager() {
		return new ResponseEntity<>(userService.getAllActiveManager(), HttpStatus.OK);
	}

}
