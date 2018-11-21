package com.be.millipore.template.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.be.millipore.constant.APIConstant;
import com.be.millipore.template.beans.TemplateUser;
import com.be.millipore.template.service.TemplateUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = APIConstant.REST_BASE_URL + APIConstant.REST_TASK_TEMPLATE_URL)
@Api(tags = { APIConstant.TASK_TEMPLATE_TAG })
public class TaskTemplateController {

	@Autowired
	private TemplateUserService templateUserService;

// (1). ****** CREATE A TASK TEMPLATE USER ****//

	@ApiOperation(value = APIConstant.CREATE_TASK_TEMPLATE_USER)
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody TemplateUser templateUser) throws JSONException {
		JSONObject jsonObject = new JSONObject();

		TemplateUser existingTemplateUser = templateUserService.save(templateUser);
		if (existingTemplateUser == null) {
			return new ResponseEntity<>(
					jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.SOMETHING_WENT_WRONG).toString(),
					HttpStatus.OK);

		}
		return new ResponseEntity<>(
				jsonObject.put(APIConstant.STATUS_RESPONSE, APIConstant.TEMPLATE_TASK_USER_CREATED).toString(),
				HttpStatus.OK);

	}

}
