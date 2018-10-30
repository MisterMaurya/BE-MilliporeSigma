package com.be.millipore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.beans.User;
import com.be.millipore.beans.UserRole;
import com.be.millipore.repository.UserRepo;
import com.be.millipore.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public User save(User user) {
		return userRepo.save(user);
	}

	@Override
	public User findByEmail(String email) {
		User user = null;
		user = userRepo.findByEmail(email);
		return user;
	}

	@Override
	public User findByMobile(String mobile) {
		User user = null;
		user = userRepo.findByMobile(mobile);
		return user;
	}

	@Override
	public User findByUserId(String userId) {
		User user = null;
		user = userRepo.findByUserId(userId);
		return user;
	}

	@Override
	public List<User> getAllUser() {
		List<User> user = new ArrayList<>();
		userRepo.findAll().forEach(e -> user.add(e));
		return user;
	}

	@Override
	public User getUser(Long id) {
		boolean isExists = false;
		isExists = userRepo.findById(id).isPresent();
		if (isExists == false) {
			return null;
		}
		return userRepo.findById(id).get();
	}

	@Override
	public void updateUser(Long id) {

	}

	@Override
	public void deleteUser(Long id, User user) {
		user.setStatus("N");
		userRepo.save(user);
	}

	@Override
	public User getLineManager(Long lineManagerId) {
		boolean isExists = false;
		User user = null;

		isExists = userRepo.findById(lineManagerId).isPresent();
		if (isExists) {
			user = getUser(lineManagerId);
			if (user.getStatus().equals("Y")) {
				for (UserRole i : user.getRole()) {
					if (i.getUserRole().equals("Manager")) {
						return user;
					} else {
						return null;
					}
				}
			} else {
				return null;
			}
		}
		return null;
	}

}
