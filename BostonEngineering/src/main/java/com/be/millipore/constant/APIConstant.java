package com.be.millipore.constant;

public class APIConstant {

	public static final String EMAIL_NOT_EXISTS = "Please enter valid email-id";
	public static final String EMAIL_ALREADY_EXISTS = "Email already exists please try with another email-id";
	public static final String MOBILE_NO_ALREADY_EXISTS = "Mobile no. already in use please use different mobile no.";
	public static final String USER_SUCCESSFULLY_CREATED = "User successfully created";
	public static final String USER_NOT_EXISTS = "Please enter valid user id. User id is not exists";
	public static final String USERID_ALREADY_EXISTS = "UserId Already exists";
	public static final String RESPONSE_ERROR_MESSAGE = "Error message";
	public static final String USER_SUCCESSFULLY_UPDATED = "User successfully updated";
	public static final String USER_CREATE_STATUS = "create";
	public static final String USER_UPDATE_STATUS = "update";
	public static final String USER_ROLE_NOT_EXISTS = "User role not exists";
	public static final String LINE_MANAGER_IS_STATUS = "Line manager is not exists/Line manager status is inactive";
	public static final String STATUS_RESPONSE = "status";
	public static final String MANAGER_NAME = "manager name";
	public static final String USER_Details = "User details";
	public static final String USER_IS_ENABLE = "User is status enable";
	public static final String USER_IS_DISABLE = "User is status  disable";
	public static final String ORGANISATION_EMAIL = "noreply@boston.com";
	public static final String SUBJECT = "Welcome to Boston!";
	public static final String SOMETHING_WENT_WRONG = "something went wrong";

//*** REST BASE URL***//

	public static final String REST_BASE_URL = "/boston/";
	public static final String REST_USER_URL = "/user";
	public static final String REST_ID_PARAM = "/{id}";
	public static final String REST_ID_STATUS = "/status";
	public static final String REST_USER_ROLE = "/role";
	public static final String ACTIVE_USERS = "/allActive";
	public static final String ALL_ACTIVE_MANAGER = "/allActiveManager";
	public static final String VERIFY = "/verify";
	public static final String USER_STATUS = "status";
	public static final String FORGOT = "/forgot";

//*** USER API OPERATIONS ***//	

	public static final String USER_CREATE = "Create A New User";
	public static final String USER_UPDATE = "Update A User";
	public static final String GET_ONE_USER = "Get A User";
	public static final String GET_ALL_USER = "Get All User";
	public static final String CHANGE_STATUS = "Change User Status";
	public static final String GET_ALL_ACIVE_MANAGER = "Get All Active Manager";

//*** USER ROLE API OPERATIONS ***//

	public static final String GET_ALL_USER_ROLE = "Get All User Role";

//*** USER LOGIN API OPERATIONS ***//

	public static final String USER_LOGIN = "User Login";

//*** USER VERIFY  API OPERATIONS ***//	

	public static final String VERIFY_USER = "Verify user";

//*** USER FORGOT PASSWORD  API OPERATIONS ***//	

	public static final String FORGOT_PASSWORD = "Forgot password";

//*** SWAGGER TAG AND DESCRIPTION   ***

	public static final String USER_CONTROLLER_TAG = "USER CONTROLLER APIs";
	public static final String USER_ROLE_CONTROLLER_TAG = "USER ROLE CONTROLLER APIs";
	public static final String USER_LOGIN_TAG = "USER_LOGIN_APIs";

	public static final String USER_CONTROLLER_DESCRIPTION = "OPERATIONS PERTAINING TO BOSTON USER";
	public static final String USER_ROLE_CONTROLLER_DESCRIPTION = "BOSTON USER ROLE";
	public static final String USER_LOGIN_DESCRIPTION = "USER LOGIN OPERATION";

//*** SWAGGER  OPERATION NOTE   ***	

	public static final String USER_CREATE_NOTE = "The User is created by the Admin";

//*** SWAGGER  APIRESPONSES MESSAGE   ***

	public static final String FORBIDDEN = "Accessing the resource you were trying to reach is forbidden";
	public static final String NOT_FOUND = "The resource you were trying to reach is not found";
	public static final String NOT_AUTHORIZED = "You are not authorized to view the resource";

//*** OTP RESPONSES MESSAGE   ***	

	public static final String PASSWORD_NOT_MATCH = "Password and confirm password not match";
	public static final String OTP_NOT_VALID = "Please Enter valid One Time Password(OTP)";
	public static final String VERIFY_SUCCESSFULLY = "User Successfully verified";
	public static final String USERID_ALREADY_VERIFIED = "User already verified";

}
