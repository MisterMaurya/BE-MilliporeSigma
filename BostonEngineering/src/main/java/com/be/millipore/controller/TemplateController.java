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

	@ApiOperation(value = APIConstant.GET_TEMPLATE)
	@RequestMapping(value = APIConstant.REST_ID_PARAM, method = RequestMethod.POST, consumes = {
			APIConstant.REST_JSON_CONTENT_TYPE }, produces = { APIConstant.REST_JSON_CONTENT_TYPE })
	public ResponseEntity<?> getTemplate(@PathVariable Long id, @RequestBody TemplateDto templateDto) throws Exception {
		Template existingTemplate = templateService.findById(id);
		User existingUser = userService.findById(templateDto.getUserId());
		Organisation existingOrganisation = organisationService.findById(templateDto.getOrganisationId());
		Department existingDepartment = departmentService.findById(templateDto.getDepartmentId());
		String content = existingTemplate.getContent();
		Map<String, String> valuesMap = new HashMap<String, String>();
		List<String> dynamicFields = templateService.getAllDynamicFields(content);
		Iterator<String> iterator = dynamicFields.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			System.out.println("Key--->" + key);

			if (!key.startsWith("user.department") && key.startsWith("user")) {
				System.out.println("--->>>>>" + key.substring(5, key.length()));
				Object object = templateService.getValue(existingUser.getClass(), key.substring(5), existingUser);
				System.out.println("===" + object.toString());

				valuesMap.put(key, object.toString());
			}

			if (key.startsWith("department")) {
				valuesMap.put(key, templateService
						.getValue(existingDepartment.getClass(), key.substring(11), existingDepartment).toString());
			}

			if (key.startsWith("organisation")) {
				valuesMap.put(key, templateService
						.getValue(existingOrganisation.getClass(), key.substring(13), existingOrganisation).toString());
			}

		}
		@SuppressWarnings("deprecation")
		StrSubstitutor sub = new StrSubstitutor(valuesMap);

		@SuppressWarnings("deprecation")
		String aString = sub.replace(content);
		System.out.println("Here--> " + aString);
		existingUser.getFullName();

		return null;
	}

	@ApiOperation(value = APIConstant.TEMPLATE_CREATE)
	@RequestMapping(method = RequestMethod.POST, consumes = {
			APIConstant.REST_JSON_CONTENT_TYPE }, produces = APIConstant.REST_JSON_CONTENT_TYPE)
	public ResponseEntity<?> createTemplate(@RequestBody Template template) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		System.out.println(template.getContent());
		Template existingTemplate = templateService.save(template);
		if (existingTemplate == null) {
			jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.TEMPLATE_NOT_SAVED);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}
		jsonObject.put(APIConstant.STATUS_RESPONSE, APIConstant.TEMPLATE_SUCCESSFULLY_CREATED);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

	}

}