package com.be.millipore.service;

import java.security.Principal;

import org.json.JSONException;
import org.springframework.http.ResponseEntity;

import com.be.millipore.beans.APIAccessControl;

public interface APIAccessControlService {

	public APIAccessControl findByApiName(String apiName);

	// send the response that REST API is accessible or not
	public ResponseEntity<?> accessControl(APIAccessControl accessControl, Principal principal) throws JSONException;

}
