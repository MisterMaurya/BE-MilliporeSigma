package com.be.millipore.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.be.millipore.beans.User;
import com.be.millipore.dto.ResetPasswordDto;

public interface UserService {

	public User save(User user);

	public List<User> findAll();

	public User findById(Long id);

	public User findByEmail(String email);

	public User findByMobile(String mobile);

	public User findByUsername(String username);

	public boolean existsById(Long id);

	public void updateUser(Long id);

	// Return All Manager
	public User getLineManager(Long lineManagerId);

	// change user status
	public ResponseEntity<?> changeStatus(User user) throws JSONException;

	// check all validation with user
	public ResponseEntity<?> validateUser(User user) throws JSONException;

	// Return All Manager have status Active
	public List<User> getAllActiveManager();

	// get One user with additional details
	JSONObject getOneUser(Long id) throws JSONException; //

	// verify user
	public ResponseEntity<?> verfityUser(ResetPasswordDto resetPasswordDto) throws JSONException;

	// forgot password
	public ResponseEntity<?> forgotPassword(ResetPasswordDto resetPasswordDto) throws JSONException;

	// update password
	public ResponseEntity<?> updatePassword(Long id, String password) throws JSONException;

	// send OTP for forgot password
	public ResponseEntity<?> sendOtpForForgotPassword(String email) throws JSONException;

	// check Reset OTP Link Expired is expired or not
	public ResponseEntity<?> isOTPResetLinkExpired(String email) throws JSONException;
}
