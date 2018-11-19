package com.be.millipore.service.impl;

import java.security.Principal;
import java.util.ArrayList;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.be.millipore.beans.APIAccessControl;
import com.be.millipore.beans.User;
import com.be.millipore.beans.UserRole;
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

		ArrayList<String> roleslist = new ArrayList<>();
		if (accessControl.getAdminAccess().length() > 0)
			roleslist.add(accessControl.getAdminAccess());
		if (accessControl.getManagerAccess().length() > 0)
			roleslist.add(accessControl.getManagerAccess());
		if (accessControl.getOperatorAccess().length() > 0)
			roleslist.add(accessControl.getOperatorAccess());

		roleslist.add(accessControl.getOperatorAccess());

		User existingUser = userService.findByUserName(principal.getName());
		for (UserRole role : existingUser.getRole()) {
			for (String dbRole : roleslist) {
				if (role.getUserRole().equalsIgnoreCase(dbRole)) {
					return new ResponseEntity<>(HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
