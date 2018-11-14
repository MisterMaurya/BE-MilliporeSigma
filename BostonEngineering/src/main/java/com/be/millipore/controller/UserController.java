package com.be.millipore.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.millipore.beans.User;
import com.be.millipore.beans.UserRole;
import com.be.millipore.constant.APIConstant;
import com.be.millipore.dto.UserDto;
import com.be.millipore.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = APIConstant.REST_BASE_URL + APIConstant.REST_USER_URL)
@Api(tags = { APIConstant.USER_CONTROLLER_TAG })
public class UserController {

	@Autowired
	private UserService userService;

// (1). ****** CREATE A NEW USER ****//

	@ApiOperation(value = APIConstant.USER_CREATE, notes = APIConstant.USER_CREATE_NOTE)
	@PreAuthorize("hasRole('ADMIN')")
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN),
			@ApiResponse(code = 404, message = APIConstant.NOT_FOUND) })
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
	@PreAuthorize("hasRole('ADMIN')")
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN),
			@ApiResponse(code = 404, message = APIConstant.NOT_FOUND) })
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
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN),
			@ApiResponse(code = 404, message = APIConstant.NOT_FOUND) })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = APIConstant.REST_ID_PARAM, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getOneUser(@PathVariable Long id) throws JSONException {
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
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN), })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllUser() {
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}

// (5). ****** CHANGE USER STATUS ****//

	@ApiOperation(value = APIConstant.CHANGE_STATUS)
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN),
			@ApiResponse(code = 404, message = APIConstant.NOT_FOUND) })
	@PreAuthorize("hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN),
			@ApiResponse(code = 404, message = APIConstant.NOT_FOUND) })
	@RequestMapping(value = APIConstant.ALL_ACTIVE_MANAGER, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllActiveManager() {
		return new ResponseEntity<>(userService.getAllActiveManager(), HttpStatus.OK);
	}

// (7). VERIFY USER

	@ApiOperation(value = APIConstant.VERIFY_USER)
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN),
			@ApiResponse(code = 404, message = APIConstant.NOT_FOUND) })
	@RequestMapping(value = APIConstant.VERIFY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> verifiedUser(@RequestBody UserDto userDto) throws JSONException {
		return new ResponseEntity<>(userService.verfityUser(userDto), HttpStatus.OK);
	}

// (8). FORGOT PASSWORD 

	@ApiOperation(value = APIConstant.FORGOT_PASSWORD)
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN),
			@ApiResponse(code = 404, message = APIConstant.NOT_FOUND) })
	@RequestMapping(value = APIConstant.FORGOT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> forgotPassword(@RequestBody UserDto userDto) throws JSONException {
		return userService.forgotPassword(userDto);
	}

// (9). UPDATE PASSWORD 	

	/*
	 * @ApiOperation(value = APIConstant.UPDATE_PASSWORD_OPERATION)
	 * 
	 * @ApiResponses(value = { @ApiResponse(code = 401, message =
	 * APIConstant.NOT_AUTHORIZED),
	 * 
	 * @ApiResponse(code = 403, message = APIConstant.FORBIDDEN),
	 * 
	 * @ApiResponse(code = 404, message = APIConstant.NOT_FOUND) })
	 */

	@RequestMapping(value = APIConstant.UPDATE_PASSWORD, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updatePassword(@PathVariable("id") Long id, @RequestParam("password") String password)
			throws JSONException {
		return userService.updatePassword(id, password);
	}

}
