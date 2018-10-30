package com.be.millipore.service;

import java.util.List;

import com.be.millipore.beans.User;

public interface UserService {

	public User save(User user);

	public User findByEmail(String email);

	public User findByMobile(String mobile);

	public User findByUserId(String userId);

	public List<User> getAllUser();

	public User getUser(Long id);

	public void updateUser(Long id);

	public void deleteUser(Long id, User user);

	public User getLineManager(Long lineManagerId);
}
