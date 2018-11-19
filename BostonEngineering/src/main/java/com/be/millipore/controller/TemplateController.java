package com.be.millipore.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.be.millipore.beans.UserTemplate;
import com.be.millipore.constant.APIConstant;
import com.be.millipore.service.UserTemplateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = APIConstant.REST_BASE_URL + APIConstant.REST_USER_URL + APIConstant.REST_TEMPLATE_URL)
@Api(tags = { APIConstant.TEMPLATE_CONTROLLER_TAG })
public class TemplateController {

	@Autowired
	private UserTemplateService userTemplateService;

// (1). ****** CREATE A NEW TEMPLATE FOR USER***//	
	@ApiOperation(value = APIConstant.TEMPLATE_CREATE)
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody UserTemplate userTemplate) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		UserTemplate existingTemplate = userTemplateService.save(userTemplate);
		if (existingTemplate == null) {
			jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.TEMPLATE_NOT_SAVED);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}
		jsonObject.put(APIConstant.STATUS_RESPONSE, APIConstant.TEMPLATE_SUCCESSFULLY_CREATED);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

	}

}
