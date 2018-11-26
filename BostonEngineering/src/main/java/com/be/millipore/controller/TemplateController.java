package com.be.millipore.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.be.millipore.beans.Department;
import com.be.millipore.beans.Organisation;
import com.be.millipore.beans.Template;
import com.be.millipore.beans.User;
import com.be.millipore.constant.APIConstant;
import com.be.millipore.constant.DBConstant;
import com.be.millipore.dto.TemplateDto;
import com.be.millipore.service.DepartmentService;
import com.be.millipore.service.OrganisationService;
import com.be.millipore.service.TemplateService;
import com.be.millipore.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = APIConstant.REST_BASE_URL + APIConstant.REST_TEMPLATE_URL)
@Api(tags = { APIConstant.TEMPLATE_CONTROLLER_TAG })
public class TemplateController {

	@Autowired
	private TemplateService templateService;

	@Autowired
	private OrganisationService organisationService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private UserService userService;

	@SuppressWarnings("deprecation")
	@ApiOperation(value = APIConstant.GET_TEMPLATE)
	@RequestMapping(value = APIConstant.REST_ID_PARAM, method = RequestMethod.POST, consumes = {
			APIConstant.REST_JSON_CONTENT_TYPE }, produces = { APIConstant.REST_JSON_CONTENT_TYPE })
	public ResponseEntity<?> getTemplate(@PathVariable Long id, @RequestBody TemplateDto templateDto) throws Exception {
		Template existingTemplate = templateService.findById(id);
		JSONObject jsonObject = new JSONObject();
		if (existingTemplate == null) {
			jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.TEMPLATE_ID_NOT_EXISTS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}
		String content = existingTemplate.getContent();
		User existingUser = userService.findById(templateDto.getUserId());
		Organisation existingOrganisation = organisationService.findById(templateDto.getOrganisationId());

		if (existingUser == null) {
			jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.USER_NOT_EXISTS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}
		if (existingOrganisation == null) {
			jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.ORGANISATION_NOT_EXISTS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}

		Map<String, String> valuesMap = new HashMap<String, String>();
		List<String> dynamicFields = templateService.getAllDynamicFields(content);
		Iterator<String> iterator = dynamicFields.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			System.out.println(key);

			if (key.startsWith(DBConstant.USER_DEPARTMENT_KEY)) {
				Department department = existingUser.getDepartment();
				Object object = templateService.getValue(department.getClass(), key.substring(16), department);
				valuesMap.put(key, object.toString());
				continue;
			}

			if (key.startsWith(DBConstant.USER_ORGANISATION_KEY)) {
				Organisation organisation = departmentService.findById(existingUser.getDepartment().getId())
						.getOrganisation();

				Object object = templateService.getValue(organisation.getClass(), key.substring(18), organisation);
				valuesMap.put(key, object.toString());
				continue;
			}

			if (key.startsWith(DBConstant.USER_KEY)) {
				Object object = templateService.getValue(existingUser.getClass(), key.substring(5), existingUser);
				valuesMap.put(key, object.toString());
				continue;
			}

			if (key.startsWith(DBConstant.ORGANISATION_KEY)) {
				valuesMap.put(key, templateService
						.getValue(existingOrganisation.getClass(), key.substring(13), existingOrganisation).toString());
				continue;
			}

		}
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		jsonObject.put(DBConstant.CONTENT, sub.replace(content));
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@ApiOperation(value = APIConstant.TEMPLATE_CREATE)
	@RequestMapping(method = RequestMethod.POST, consumes = {
			APIConstant.REST_JSON_CONTENT_TYPE }, produces = APIConstant.REST_JSON_CONTENT_TYPE)
	public ResponseEntity<?> createTemplate(@RequestBody Template template) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		Template existingTemplate = templateService.save(template);
		if (existingTemplate == null) {
			templateService.keyNotFound();
			jsonObject.put(APIConstant.ERROR_MESSAGE, templateService.keyNotFound() + APIConstant.KEY_NOT_FOUND);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}
		jsonObject.put(APIConstant.STATUS_RESPONSE, APIConstant.TEMPLATE_SUCCESSFULLY_CREATED);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

	}

}