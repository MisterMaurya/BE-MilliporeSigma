package com.be.millipore.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.be.millipore.beans.User;

public interface UserService {

	public User save(User user);

	public List<User> findAll();

	public User findById(Long id);

	public void updateUser(Long id);

	public User findByEmail(String email);

	public User findByMobile(String mobile);

	public User findByUserName(String userName);

	public User getLineManager(Long lineManagerId); // Return All Manager

	public ResponseEntity<?> changeStatus(User user) throws JSONException; // change user status

	public ResponseEntity<?> validateUser(User user) throws JSONException; // check all validation with user

	public List<User> getAllActiveManager(); // Return All Manager have status Active('Y')

	JSONObject getOneUser(Long id) throws JSONException; // get One user with additional details

}