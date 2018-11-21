package com.be.millipore.template.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.be.millipore.beans.Template;
import com.be.millipore.constant.APIConstant;
import com.be.millipore.service.TemplateKeyAndValueService;
import com.be.millipore.service.TemplateService;
import com.be.millipore.template.beans.TemplateUser;
import com.be.millipore.template.service.TaskTemplateService;
import com.be.millipore.template.service.TemplateUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = APIConstant.REST_BASE_URL + APIConstant.REST_TASK_TEMPLATE_URL)
@Api(tags = { APIConstant.TASK_TEMPLATE_TAG })
public class TaskTemplateController {

	@Autowired
	private TemplateUserService templateUserService;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private TaskTemplateService taskTemplateService;

	@Autowired
	private TemplateKeyAndValueService keyAndValueService;

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

// (2). ****** CREATE A NEW TEMPLATE FOR Task Template USER***//	

	@ApiOperation(value = APIConstant.TEMPLATE_CREATE)
	@RequestMapping(value = APIConstant.CREATE_TASK_TEMPLATE_URL, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createTemplate(@RequestBody Template template) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		Template existingTemplate = taskTemplateService.save(template);
		if (existingTemplate == null) {
			jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, taskTemplateService.keyNotPresent());
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}
		jsonObject.put(APIConstant.STATUS_RESPONSE, APIConstant.TEMPLATE_SUCCESSFULLY_CREATED);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

// (3). ****** GET A Task TEMPLATE AND ADD Dynamic Values **//	

	@ApiOperation(value = APIConstant.GET_TEMPLATE)
	@RequestMapping(value = APIConstant.REST_ID_PARAM, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getTaskTemplate(@PathVariable Long id, @RequestBody TemplateUser templateUser)
			throws JSONException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		JSONObject jsonObject = new JSONObject();
		Template existingTemplate = templateService.findById(id);
		if (existingTemplate == null) {
			jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.TEMPLATE_ID_NOT_EXISTS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}

		TemplateUser existingTemplateUser = null;
		existingTemplateUser = templateUserService.findByEmail(templateUser.getEmail());
		if (existingTemplateUser == null) {
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
			valuesMap.put(key, keyAndValueService.getTaskTemplateUserFieldValue(key, existingTemplateUser).toString());
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
