package com.be.millipore.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.be.millipore.beans.Department;
import com.be.millipore.beans.User;
import com.be.millipore.beans.UserRole;
import com.be.millipore.constant.APIConstant;
import com.be.millipore.constant.DBConstant;
import com.be.millipore.dto.UserDto;
import com.be.millipore.enums.IsActive;
import com.be.millipore.enums.IsExpired;
import com.be.millipore.repository.UserRepo;
import com.be.millipore.repository.UserRoleRepo;
import com.be.millipore.service.DepartmentService;
import com.be.millipore.service.EmailService;
import com.be.millipore.service.OrganisationService;
import com.be.millipore.service.UserService;
import com.sendgrid.Response;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserRoleRepo userRoleRepo;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private EmailService emailService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private OrganisationService organisationService;

	@Override
	public User save(User user) {
		User newUser = null;
		String otp = generateOTP();
		user.setOtp(bcryptEncoder.encode(user.getOtp()));
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		user.setIsExpired(IsExpired.NOT_GENERATE);
		user.setLastPassword(DBConstant.LAST_PASSWORD_PENDING);
		try {
			newUser = userRepo.save(user);
		} catch (Exception e) {
			System.out.println("Exception : " + e);
			return newUser;
		}
		newUser = userRepo.save(user);
		Response response = emailService.sendOtpTemplate(APIConstant.ORGANISATION_EMAIL, user.getEmail(),
				APIConstant.SUBJECT, APIConstant.VERIFY_TEMPLATE_ID, otp);
		System.out.println(
				"Email Status : " + (response.getStatusCode() == 202 ? "Send successfully" : "Unable to send"));
		return newUser;
	}

	@Override
	public User findByEmail(String email) {
		User existingUser = null;
		existingUser = userRepo.findByEmail(email);
		return existingUser;
	}

	@Override
	public User findByMobile(String mobile) {
		User existingUser = null;
		existingUser = userRepo.findByMobile(mobile);
		return existingUser;
	}

	@Override
	public User findByUsername(String username) {
		User existingUser = null;
		existingUser = userRepo.findByUsername(username);
		return existingUser;
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
			if (user.getIsActive().equals(IsActive.Y)) {
				for (UserRole i : user.getRole()) {
					if (i.getRole().equals("Manager")) {
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

		if (user.getIsActive().equals(IsActive.Y)) {
			user.setIsActive(IsActive.N);
			userRepo.save(user);
			return new ResponseEntity<>(jsonObject.put(APIConstant.USER_STATUS, APIConstant.USER_IS_DISABLE).toString(),
					HttpStatus.OK);
		} else {
			user.setIsActive(IsActive.Y);
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

		if (existingUser != null && existingUser.getId() != user.getId()) {
			return new ResponseEntity<String>(
					jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.EMAIL_ALREADY_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);

		}

		existingUser = findByMobile(user.getMobile());
		if (existingUser != null && existingUser.getId() != user.getId()) {
			return new ResponseEntity<String>(
					jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.MOBILE_NO_ALREADY_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);
		}

		existingUser = findByUsername(user.getUsername());
		if (existingUser != null && existingUser.getId() != user.getId()) {
			return new ResponseEntity<>(
					jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.USERID_ALREADY_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);

		}

		if (!departmentService.existsById(user.getDepartment().getId())) {
			return new ResponseEntity<>(
					jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.DEPARTMENT_NOT_EXISTS).toString(),
					HttpStatus.BAD_REQUEST);

		}

		for (UserRole role : user.getRole()) {
			if (userRoleRepo.findById(role.getId()).isPresent()) {
				if (role.getRole().equals("Operator")) {
					existingUser = getLineManager(user.getLineManagerId());
					if (existingUser == null) {
						return new ResponseEntity<>(jsonObject
								.put(APIConstant.ERROR_MESSAGE, APIConstant.LINE_MANAGER_IS_STATUS).toString(),
								HttpStatus.OK);
					}
				}
			} else {
				return new ResponseEntity<>(
						jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.USER_ROLE_NOT_EXISTS).toString(),
						HttpStatus.OK);
			}
		}
		if (!user.getIsActive().equals(IsActive.N)) {
			user.setIsActive(IsActive.N);
		}

		return null;
	}

	@Override
	public List<User> getAllActiveManager() {
		List<User> activeManager = new ArrayList<>();
		for (User user : userRepo.findAll()) {
			if (user.getIsActive().equals(IsActive.Y)) {
				for (UserRole userRole : user.getRole()) {
					if (userRole.getRole().equals("MANAGER")) {
						activeManager.add(user);
					}
				}
			}

		}
		for (User user : activeManager) {
			System.out.println("aa " + user.getFullName());
		}

		return activeManager;
	}

	@Override
	public JSONObject getOneUser(Long id) throws JSONException {
		boolean isExists = false;
		JSONObject jsonObject = new JSONObject();
		JSONObject temp = null;

		JSONArray jsonArray = new JSONArray();
		isExists = userRepo.findById(id).isPresent();
		if (isExists == false) {
			return null;
		}

		User user = userRepo.findById(id).get();

		jsonObject.put("id", user.getId());
		jsonObject.put("userName", user.getUsername());
		jsonObject.put("email", user.getEmail());
		jsonObject.put("fullName", user.getFullName());
		jsonObject.put("department", user.getDepartment());
		jsonObject.put("countryCode", user.getCountryCode());
		jsonObject.put("mobile", user.getMobile());
		jsonObject.put("lineManageId", user.getLineManagerId());
		jsonObject.put("status", user.getIsActive());
		jsonObject.put("password", user.getPassword());
		Department department = departmentService.findById(user.getDepartment().getId());
		jsonObject.put("Department Name", department.getName());
		jsonObject.put("Organistion Name",
				organisationService.findById(department.getOrganisation().getId()).getName());
		for (UserRole userRole : user.getRole()) {
			temp = new JSONObject();
			temp.put("userRoleId", userRole.getId());
			temp.put("userRole", userRole.getRole());
			jsonArray.put(temp);
			temp = null;
		}
		jsonObject.put("role", jsonArray);

		return jsonObject;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthority(user));

	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRole().forEach(role -> {
			// authorities.add(new SimpleGrantedAuthority(role.getName()));
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
		});
		return authorities;
		// return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

// ******* generateOTP() will be generate the 6 digit OTP(One Time Password)

	private String generateOTP() {
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		Random rnd = new Random();
		String generate = "";
		for (int i = 0; i < 6; i++) {
			generate = generate + chars[rnd.nextInt(chars.length)];
		}
		return generate;
	}

// ******* matchOTPAndPassword(UserDto userDto) will check Email's OTP and user entered OTP are match or not  and also will check password and confirm password are equals or  not

	private ResponseEntity<?> matchOTPAndPassword(UserDto userDto) throws JSONException {
		User existingUser = null;
		JSONObject jsonObject = new JSONObject();
		existingUser = userRepo.findByEmail(userDto.getEmail());

		// if email not exists

		if (existingUser == null) {
			jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.USER_NOT_EXISTS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}

		// if password and and confirm password dose'nt match

		if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
			jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.PASSWORD_NOT_MATCH);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}

		// if OTP dose'nt match with email received OTP
		if (!userDto.getOtp().equals(existingUser.getOtp())) {
			jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.OTP_NOT_VALID);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> verfityUser(UserDto userDto) throws JSONException {
		User existingUser = null;
		JSONObject jsonObject = new JSONObject();
		existingUser = userRepo.findByEmail(userDto.getEmail());

		// if user already verified
		if (existingUser.getIsActive().equals(IsActive.Y)) {
			jsonObject.put(APIConstant.STATUS_RESPONSE, APIConstant.USERID_ALREADY_VERIFIED);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}

		ResponseEntity<?> responseEntity = matchOTPAndPassword(userDto);
		if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			return responseEntity;
		}
		existingUser.setPassword(bcryptEncoder.encode(userDto.getPassword())); // update password
		existingUser.setIsActive(IsActive.Y); // update status
		existingUser.setOtp("");
		userRepo.save(existingUser);
		jsonObject.put(APIConstant.STATUS_RESPONSE, APIConstant.VERIFY_SUCCESSFULLY);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

	}

// ******* isPasswordAlreadyUse(arg1 , arg2) will check password already used or not

	private ResponseEntity<?> isPasswordAlreadyUse(String password, User user) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		if (bcryptEncoder.matches(password, user.getPassword())) {
			jsonObject.put(APIConstant.ERROR_MESSAGE, "you entered your current password please a different password");
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);

		}
		if (bcryptEncoder.matches(password, user.getLastPassword())) {
			jsonObject.put(APIConstant.ERROR_MESSAGE,
					"you can not use your last password! Please you a different password");
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> forgotPassword(UserDto userDto) throws JSONException {
		User existingUser = null;
		ResponseEntity<?> responseEntity = null;
		JSONObject jsonObject = new JSONObject();
		existingUser = userRepo.findByEmail(userDto.getEmail());
		responseEntity = matchOTPAndPassword(userDto);
		if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			return responseEntity;
		}
		responseEntity = isPasswordAlreadyUse(userDto.getPassword(), existingUser);
		if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			return responseEntity;
		}

		existingUser.setLastPassword(existingUser.getPassword());
		existingUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));
		existingUser.setOtp("XXXX");
		userRepo.save(existingUser);
		jsonObject.put(APIConstant.STATUS_RESPONSE, APIConstant.PASSWORD_SUCESSFULLY_RESET);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> updatePassword(Long id, String password) throws JSONException {
		User existingUser = null;
		ResponseEntity<?> responseEntity = null;
		JSONObject jsonObject = new JSONObject();
		existingUser = findById(id);

		if (existingUser == null) {
			jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.USER_NOT_EXISTS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}

		responseEntity = isPasswordAlreadyUse(password, existingUser);
		if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			return responseEntity;
		}

		existingUser.setLastPassword(existingUser.getPassword());
		existingUser.setPassword(bcryptEncoder.encode(password));
		userRepo.save(existingUser);

		jsonObject.put(APIConstant.STATUS_RESPONSE, APIConstant.PASSWORD_SUCESSFULLY_UPDATED);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> sendOtpForForgotPassword(String email) throws JSONException {
		User existingUser = null;
		JSONObject jsonObject = new JSONObject();
		existingUser = userRepo.findByEmail(email);
		if (existingUser == null) {
			jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.USER_NOT_EXISTS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}
		String otp = generateOTP();
		existingUser.setOtp(otp);
		existingUser.setIsExpired(IsExpired.Y);

		Response response = emailService.sendOtpTemplate(APIConstant.ORGANISATION_EMAIL, existingUser.getEmail(),
				APIConstant.SUBJECT, APIConstant.FORGOT_TEMPLATE_ID, otp);
		userRepo.save(existingUser);
		System.out.println(response.getStatusCode());
		jsonObject.put(APIConstant.STATUS_RESPONSE, APIConstant.OTP_SEND);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> isOTPResetLinkExpired(String email) throws JSONException {
		User existingUser = null;
		JSONObject jsonObject = new JSONObject();
		existingUser = userRepo.findByEmail(email);

		if (existingUser == null) {
			jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.USER_NOT_EXISTS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}

		if (existingUser.getIsExpired().equals(IsExpired.Y)) {
			existingUser.setIsExpired(IsExpired.N);
			userRepo.save(existingUser);
			jsonObject.put(APIConstant.STATUS_RESPONSE, APIConstant.LINK_NOT_EXPIRED);
			return new ResponseEntity<>(HttpStatus.OK);
		}

		jsonObject.put(APIConstant.ERROR_MESSAGE, APIConstant.LINK_EXPIRED);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);

	}

	public String h() {

		return findById((long) 1).getEmail();
	}

}
