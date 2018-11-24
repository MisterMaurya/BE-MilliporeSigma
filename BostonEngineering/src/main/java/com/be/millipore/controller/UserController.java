package com.be.millipore.controller;

import java.security.Principal;

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

import com.be.millipore.beans.APIAccessControl;
import com.be.millipore.beans.User;
import com.be.millipore.beans.UserRole;
import com.be.millipore.constant.APIConstant;
import com.be.millipore.dto.UserDto;
import com.be.millipore.service.APIAccessControlService;
import com.be.millipore.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Pawan-pc
 *
 */

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = APIConstant.REST_BASE_URL + APIConstant.REST_USER_URL)
@Api(tags = { APIConstant.USER_CONTROLLER_TAG })
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private APIAccessControlService accessControlService;

	/**************************************************************************
	 * (1). CREATE A NEW USER
	 **************************************************************************/

	/**
	 * @param user
	 * @return Response code 200 if user successfully created
	 * @throws JSONException
	 */

	@ApiOperation(value = APIConstant.USER_CREATE, notes = APIConstant.USER_CREATE_NOTE)
	// @PreAuthorize("hasRole('ADMIN')")
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN),
			@ApiResponse(code = 404, message = APIConstant.NOT_FOUND) })
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody User user) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		User existingUser = null;
		ResponseEntity<?> responseEntity = userService.validateUser(user);
		if (responseEntity != null) {
			return responseEntity;
		}

		existingUser = userService.save(user);
		System.out.println(existingUser.getFullName() + " Succeffully created");
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

			jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.USER_NOT_EXISTS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}

		jsonObject = userService.getOneUser(id);
		for (UserRole userRole : existingUser.getRole()) {
			if (userRole.getRole().equals("Operator")) {
				User getManagerDetails = userService.findById(existingUser.getLineManagerId());
				jsonObject.put("Manager Name", getManagerDetails.getFullName());
				jsonObject.put("Manager Email", getManagerDetails.getEmail());
				jsonObject.put("Manager Mobile", getManagerDetails.getMobile());

			}
		}

		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	/**************************************************************************
	 * (4). GET LIST OF ALL USER
	 **************************************************************************/

	/**
	 * @param principal
	 * @return
	 * @throws JSONException
	 */

	@ApiOperation(value = APIConstant.GET_ALL_USER)
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN), })
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllUser(Principal principal) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		/*
		 * APIAccessControl accessControl =
		 * accessControlService.findByApiName(DBConstant.GET_ALL_USER_RESOURCE);
		 * ResponseEntity<?> responseEntity =
		 * accessControlService.accessControl(accessControl, principal); if
		 * (responseEntity.getStatusCode() != HttpStatus.OK) {
		 * jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.FORBIDDEN); return new
		 * ResponseEntity<>(jsonObject.toString(), HttpStatus.UNAUTHORIZED); }
		 */
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}

	/**************************************************************************
	 * (5). CHANGE USER STATUS
	 **************************************************************************/

	/**
	 * @param id
	 * @return Response code 200 if User status successfully updated
	 * @throws JSONException
	 */

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
					jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.USER_NOT_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);
		}

		return userService.changeStatus(user);
	}

	/**************************************************************************
	 * (6). GET ALL ACTIVE LINE MANAGER
	 **************************************************************************/

	/**
	 * @return all active line manger
	 */
	@ApiOperation(value = APIConstant.GET_ALL_ACIVE_MANAGER)
	@PreAuthorize("hasRole('ADMIN')")
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN),
			@ApiResponse(code = 404, message = APIConstant.NOT_FOUND) })
	@RequestMapping(value = APIConstant.ALL_ACTIVE_MANAGER, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllActiveManager() {
		return new ResponseEntity<>(userService.getAllActiveManager(), HttpStatus.OK);
	}

	/**************************************************************************
	 * (7). VERIFY USER
	 **************************************************************************/

	/**
	 * @param userDto
	 * @return Response code 200 if User status successfully verified
	 * @throws JSONException
	 */

	@ApiOperation(value = APIConstant.VERIFY_USER)
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN),
			@ApiResponse(code = 404, message = APIConstant.NOT_FOUND) })
	@RequestMapping(value = APIConstant.VERIFY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> verifiedUser(@RequestBody UserDto userDto) throws JSONException {
		return userService.verfityUser(userDto);
	}

// (9). SEND OTP FOR FORGOT PASSWORD  

	@ApiOperation(value = APIConstant.SEND_OTP_FOR_FORGOT_PASSWORD)
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN),
			@ApiResponse(code = 404, message = APIConstant.NOT_FOUND) })
	@RequestMapping(value = APIConstant.RESET, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendOtpForForgotPassword(String email) throws JSONException {
		return userService.sendOtpForForgotPassword(email);
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

	/**************************************************************************
	 * (10). UPDATE PASSWORD
	 **************************************************************************/

	/**
	 * @param id
	 * @param password
	 * @return Response code 200 if User status successfully update password
	 * @throws JSONException
	 */
	@ApiOperation(value = APIConstant.UPDATE_PASSWORD_OPERATION)
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN),
			@ApiResponse(code = 404, message = APIConstant.NOT_FOUND) })
	@RequestMapping(value = APIConstant.UPDATE_PASSWORD, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updatePassword(@PathVariable("id") Long id,
			@ApiParam(name = "password") @RequestParam("password") String password) throws JSONException {
		return userService.updatePassword(id, password);
	}

// (11). RESET LINK VALIDATE	

	@ApiOperation(value = APIConstant.RESET_LINK_VALIDATE)
	@ApiResponses(value = { @ApiResponse(code = 401, message = APIConstant.NOT_AUTHORIZED),
			@ApiResponse(code = 403, message = APIConstant.FORBIDDEN),
			@ApiResponse(code = 404, message = APIConstant.NOT_FOUND) })
	@RequestMapping(value = APIConstant.RESET, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public ResponseEntity<?> reset(@ApiParam(name = "email") @RequestParam("email") String email) throws JSONException {
		return userService.isOTPResetLinkExpired(email);
	}

// (12). UPDATE ACCESS CONTROL

	@ApiOperation(value = APIConstant.UPDATE_ACCESS_CONTROL)
	@RequestMapping(value = APIConstant.ACCESS_CONTROL_UPDATE, method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateAccessControl(@PathVariable Long id, @RequestBody APIAccessControl accessControl)
			throws JSONException {
		return accessControlService.updateAccessControl(id, accessControl);
	}

}
