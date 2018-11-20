package com.be.millipore.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.be.millipore.beans.Template;
import com.be.millipore.beans.User;
import com.be.millipore.constant.APIConstant;
import com.be.millipore.dto.UserTemplateDto;
import com.be.millipore.service.TemplateKeyAndValueService;
import com.be.millipore.service.TemplateService;
import com.be.millipore.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@SuppressWarnings("deprecation")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = APIConstant.REST_BASE_URL + APIConstant.REST_USER_URL + APIConstant.REST_TEMPLATE_URL)
@Api(tags = { APIConstant.TEMPLATE_CONTROLLER_TAG })
public class TemplateController {

	@Autowired
	private TemplateService templateService;

	@Autowired
	private UserService userService;

	@Autowired
	private TemplateKeyAndValueService keyAndValueService;

// (1). ****** CREATE A NEW TEMPLATE FOR USER***//	

	@ApiOperation(value = APIConstant.TEMPLATE_CREATE)
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createTemplate(@RequestBody Template userTemplate) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		Template existingTemplate = templateService.save(userTemplate);
		if (existingTemplate == null) {
			jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.TEMPLATE_NOT_SAVED);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}
		jsonObject.put(APIConstant.STATUS_RESPONSE, APIConstant.TEMPLATE_SUCCESSFULLY_CREATED);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

	}

// (2). ****** GET A TEMPLATE AND Dynamic Values **//	

	@ApiOperation(value = APIConstant.GET_TEMPLATE)
	@RequestMapping(value = APIConstant.REST_ID_PARAM, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getTemplate(@PathVariable Long id, @RequestBody UserTemplateDto userTemplateDto)
			throws JSONException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		JSONObject jsonObject = new JSONObject();
		Template existingTemplate = templateService.findById(id);
		if (existingTemplate == null) {
			jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.TEMPLATE_ID_NOT_EXISTS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}

		User existingUser = null;
		existingUser = userService.findByEmail(userTemplateDto.getEmail());
		if (existingUser == null) {
			jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.EMAIL_NOT_EXISTS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}

		String content = existingTemplate.getContent();
		List<String> getDynamicData = templateService.getAllDynamicData(content);
		System.out.println(getDynamicData);
		Map<String, String> valuesMap = new HashMap<String, String>();
		Iterator<String> iterator = getDynamicData.iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();
			valuesMap.put(key, keyAndValueService.getUserFieldValue(key, existingUser).toString());
		}
		String mappedContent = null;
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		try {
			mappedContent = sub.replace(content);
		} catch (Exception e) {
			jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.PROVIDE_VALID_DYNAMIC_DATA);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}

		jsonObject.put(APIConstant.TEMPLATE_CONTENT, mappedContent);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

	}
}
