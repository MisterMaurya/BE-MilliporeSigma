package com.be.millipore.service;

import java.util.List;

import org.json.JSONException;
import org.springframework.http.ResponseEntity;

import com.be.millipore.beans.User;

public interface UserService {

	public User save(User user);

	public User findByEmail(String email);

	public User findByMobile(String mobile);

	public User findByUserId(String userId);

	public List<User> findAll();

	public User findById(Long id);

	public void updateUser(Long id);

	public User getLineManager(Long lineManagerId);

	public ResponseEntity<?> changeStatus(User user) throws JSONException;

	public ResponseEntity<?> validateUser(User user) throws JSONException;

	public List<User> getAllActiveManager();
}
