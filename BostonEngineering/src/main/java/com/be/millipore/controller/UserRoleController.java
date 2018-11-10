package com.be.millipore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.be.millipore.beans.UserRole;
import com.be.millipore.constant.APIConstant;
import com.be.millipore.service.UserRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = APIConstant.REST_BASE_URL + APIConstant.REST_USER_URL + APIConstant.REST_USER_ROLE)
@Api(tags = { APIConstant.USER_ROLE_CONTROLLER_TAG })
public class UserRoleController {
	@Autowired
	private UserRoleService userRoleService;

//**** GET ALL ROLE LIST ****//

	@ApiOperation(value = APIConstant.GET_ALL_USER_ROLE)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserRole> getUserRoleList() {
		return userRoleService.findAllUserRole();
	}

}
