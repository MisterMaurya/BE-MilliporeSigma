package com.be.millipore.service.impl;

import java.security.Principal;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.be.millipore.beans.APIAccessControl;
import com.be.millipore.beans.User;
import com.be.millipore.beans.UserRole;
import com.be.millipore.constant.APIConstant;
import com.be.millipore.repository.APIAccessControlRepo;
import com.be.millipore.service.APIAccessControlService;
import com.be.millipore.service.UserService;

@Service
public class APIAccessControlServiceImpl implements APIAccessControlService {

	@Autowired
	private APIAccessControlRepo accessControlRepo;

	@Autowired
	private UserService userService;

	@Override
	public APIAccessControl findByApiName(String apiName) {
		return accessControlRepo.findByApiName(apiName);
	}

	@Override
	public ResponseEntity<?> accessControl(APIAccessControl accessControl, Principal principal) throws JSONException {

		ArrayList<String> access = new ArrayList<>();
		access.add(accessControl.getAdminAccessAPI().toString());
		access.add(accessControl.getManagerAccessAPI().toString());
		access.add(accessControl.getOperatorAccessAPI().toString());
		User existingUser = userService.findByUsername(principal.getName());
		for (UserRole role : existingUser.getRole()) {
			for (String userRole : access) {
				if (role.getRole().equalsIgnoreCase(userRole)) {
					return new ResponseEntity<>(HttpStatus.OK);
				}
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@Override
	public ResponseEntity<?> updateAccessControl(Long id, APIAccessControl accessControl) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		boolean isAPIExists = false;
		isAPIExists = accessControlRepo.findById(id).isPresent();
		if (!isAPIExists) {
			jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.ACCESS_CONTROL_ID_NOT_EXISIS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}

		accessControlRepo.save(accessControl);
		jsonObject.put(APIConstant.STATUS_RESPONSE, APIConstant.ACCESS_CONTROL_UPDATED);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

	}

}
