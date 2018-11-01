package com.be.millipore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.be.millipore.apiconstant.APIConstant;
import com.be.millipore.beans.User;
import com.be.millipore.beans.UserRole;
import com.be.millipore.repository.UserRepo;
import com.be.millipore.repository.UserRoleRepo;
import com.be.millipore.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserRoleRepo userRoleRepo;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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
	public List<User> findAll() {
		List<User> user = new ArrayList<>();
		userRepo.findAll().forEach(e -> user.add(e));
		return user;
	}

	@Override
	public User findById(Long id) {
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
	public User getLineManager(Long lineManagerId) {
		boolean isExists = false;
		User user = null;

		isExists = userRepo.findById(lineManagerId).isPresent();
		if (isExists) {
			user = findById(lineManagerId);
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

	@Override
	public ResponseEntity<?> changeStatus(User user) throws JSONException {
		JSONObject jsonObject = new JSONObject();

		if (user.getStatus().equals("Y")) {
			user.setStatus("N");
			userRepo.save(user);
			return new ResponseEntity<>(jsonObject.put(APIConstant.USER_STATUS, APIConstant.USER_IS_DISABLE).toString(),
					HttpStatus.OK);
		} else {
			user.setStatus("Y");
			userRepo.save(user);
			return new ResponseEntity<>(jsonObject.put(APIConstant.USER_STATUS, APIConstant.USER_IS_ENABLE).toString(),
					HttpStatus.OK);
		}

	}

	@Override
	public ResponseEntity<?> validateUser(User user) throws JSONException {
		User existingUser = null;
		JSONObject jsonObject = new JSONObject();

		existingUser = findByEmail(user.getEmail());

		//

		if (existingUser.getEmail() != null && existingUser.getId() != user.getId()) {
			return new ResponseEntity<String>(
					jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.EMAIL_ALREADY_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);

		}

		existingUser = findByMobile(user.getMobile());

		if (existingUser != null && existingUser.getId() != user.getId()) {

			return new ResponseEntity<String>(
					jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.MOBILE_NO_ALREADY_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);
		}

		existingUser = findByUserId(user.getUserId());

		if (existingUser != null && existingUser.getId() != user.getId()) {
			return new ResponseEntity<>(
					jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.USERID_ALREADY_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);

		}

		for (UserRole i : user.getRole()) {
			if (userRoleRepo.findById(i.getUserRoleId()).isPresent()) {
				if (i.getUserRole().equals("Operator")) {
					existingUser = getLineManager(user.getLineManageId());
					if (existingUser == null) {
						return new ResponseEntity<>(jsonObject
								.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.LINE_MANAGER_IS_STATUS).toString(),
								HttpStatus.OK);
					}
				}
			} else {
				return new ResponseEntity<>(
						jsonObject.put(APIConstant.RESPONSE_ERROR_MESSAGE, APIConstant.USER_ROLE_NOT_EXISTS).toString(),
						HttpStatus.OK);
			}
		}
		if (!user.getStatus().equals("N")) {
			user.setStatus("Y");
		}
		return null;
	}

	@Override
	public List<User> getAllActiveManager() {
		List<User> activeManager = new ArrayList<>();
		for (User user : userRepo.findAll()) {
			if (user.getStatus().equals("Y")) {
				for (UserRole userRole : user.getRole()) {
					if (userRole.getUserRole().equals("Manager")) {
						activeManager.add(user);
					}
				}
			}

		}
		return activeManager;
	}

	@Override
	public JSONObject getOneUser(Long id) throws JSONException {
		boolean isExists = false;
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();

		JSONArray jsonArray = new JSONArray();
		isExists = userRepo.findById(id).isPresent();
		if (isExists == false) {
			return null;
		}
		User user = userRepo.findById(id).get();
		jsonObject.put("id", user.getId());
		jsonObject.put("userId", user.getUserId());
		jsonObject.put("email", user.getEmail());
		jsonObject.put("fullName", user.getFullName());
		jsonObject.put("title", user.getTitle());
		jsonObject.put("department", user.getDepartment());
		jsonObject.put("countryCode", user.getCountryCode());
		jsonObject.put("mobile", user.getMobile());
		jsonObject.put("lineManageId", user.getLineManageId());
		jsonObject.put("status", user.getStatus());

		for (UserRole userRole : user.getRole()) {
			jsonObject2.put("userRoleId", userRole.getUserRoleId());
			jsonObject2.put("userRole", userRole.getUserRole());

		}

		jsonArray.put(jsonObject2);
		jsonObject.put("role", jsonArray);

		return jsonObject;
	}

}
