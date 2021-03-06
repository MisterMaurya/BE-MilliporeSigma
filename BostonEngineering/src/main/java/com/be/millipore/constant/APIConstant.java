package com.be.millipore.constant;

public class APIConstant {

	public static final String EMAIL_NOT_EXISTS = "Please enter valid email-id";
	public static final String EMAIL_ALREADY_EXISTS = "Email already exists please try with another email-id";
	public static final String MOBILE_NO_ALREADY_EXISTS = "Mobile no. already in use please use different mobile no.";
	public static final String USER_SUCCESSFULLY_CREATED = "User successfully created";
	public static final String USER_NOT_EXISTS = "Please enter valid user id. User id is not exists";
	public static final String USERNAME_ALREADY_EXISTS = "Username Already exists";
	public static final String ERROR_MESSAGE = "Error message";
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
	public static final String ACCESS_CONTROL_ID_NOT_EXISIS = "Access control id not exisis";
	public static final String ACCESS_CONTROL_UPDATED = "Access Control successfully updated";
	public static final String TEMPLATE_ID_NOT_EXISTS = "Template id not exists";
	public static final String TEMPLATE_NOT_SAVED = "Template not save! Something went wrong";
	public static final String TEMPLATE_SUCCESSFULLY_CREATED = "Template successfully created";
	public static final String TEMPLATE_CONTENT = "content";
	public static final String DEPARTMENT_NOT_EXISTS = "Please enter valid department id. Department id is not exists";

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
	public static final String RESET = "/reset";
	public static final String UPDATE_PASSWORD = "/update/passsword/{id}";
	public static final String ACCESS_CONTROL_UPDATE = "/access/control/{id}";
	public static final String REST_TEMPLATE_URL = "/template";
	public static final String REST_TASK_TEMPLATE_URL = "/task/template";
	public static final String CREATE_TASK_TEMPLATE_URL = "/create";
	public static final String REST_JSON_CONTENT_TYPE = "application/json";

//*** USER API OPERATIONS ***//	

	public static final String USER_CREATE = "Create A New User";
	public static final String USER_UPDATE = "Update A User";
	public static final String GET_ONE_USER = "Get A User";
	public static final String GET_ALL_USER = "Get All User";
	public static final String CHANGE_STATUS = "Change User Status";
	public static final String GET_ALL_ACIVE_MANAGER = "Get All Active Manager";
	public static final String VERIFY_USER = "Verify User";
	public static final String FORGOT_PASSWORD = "Forgot Password";
	public static final String RESET_LINK_VALIDATE = "Reset Link Validate ";
	public static final String SEND_OTP_FOR_FORGOT_PASSWORD = "Send Otp For Forgot Password";
	public static final String UPDATE_PASSWORD_OPERATION = "Update Password";
	public static final String UPDATE_ACCESS_CONTROL = "Update Access Control";

//*** TEMPLATE API OPERATIONS ***//		

	public static final String TEMPLATE_CREATE = "Create A New Template";
	public static final String GET_TEMPLATE = "Get A Template";

//*** USER ROLE API OPERATIONS ***//

	public static final String GET_ALL_USER_ROLE = "Get All User Role";

//*** USER LOGIN API OPERATIONS ***//

	public static final String USER_LOGIN = "User Login";

	// *** TASK TEMPLATE API OPERATIONS ***//

	public static final String CREATE_TASK_TEMPLATE_USER = "Create Template Task User";

//*** SWAGGER TAG AND DESCRIPTION   ***

	public static final String USER_CONTROLLER_TAG = "USER CONTROLLER APIs";
	public static final String USER_ROLE_CONTROLLER_TAG = "USER ROLE CONTROLLER APIs";
	public static final String USER_LOGIN_TAG = "USER_LOGIN_APIs";
	public static final String TEMPLATE_CONTROLLER_TAG = "TEMPLATE CONTROLLER APIs";
	public static final String TASK_TEMPLATE_TAG = "TASK TEMPLATE APIs";

	public static final String USER_CONTROLLER_DESCRIPTION = "OPERATIONS PERTAINING TO BOSTON USER";
	public static final String USER_ROLE_CONTROLLER_DESCRIPTION = "BOSTON USER ROLE";
	public static final String USER_LOGIN_DESCRIPTION = "USER LOGIN OPERATION";
	public static final String TEMPLATE_CONTROLLER_DESCRIPTION = "TEMPLATE OPERATIONS";
	public static final String TASK_TEMPLATE_DESCRIPTION = "TASK TEMPLATE OPERATIONS";

//*** SWAGGER  OPERATION NOTE   ***	

	public static final String USER_CREATE_NOTE = "The User is created by the Admin";

//*** SWAGGER  APIRESPONSES MESSAGE   ***

	public static final String FORBIDDEN = "Accessing the resource you were trying to reach is forbidden";
	public static final String NOT_FOUND = "The resource you were trying to reach is not found";
	public static final String NOT_AUTHORIZED = "You are not authorized to view the resource";
	public static final String INTERNAL_SERVER_ERROR = "Internal server error";

//*** OTP RESPONSES MESSAGE   ***	

	public static final String PASSWORD_NOT_MATCH = "Password and confirm password not match";
	public static final String OTP_NOT_VALID = "Please Enter valid One Time Password(OTP)";
	public static final String VERIFY_SUCCESSFULLY = "User Successfully verified";
	public static final String USERID_ALREADY_VERIFIED = "User already verified";
	public static final String PASSWORD_SUCESSFULLY_RESET = "Password successfully reset";
	public static final String PASSWORD_SUCESSFULLY_UPDATED = "Password successfully updated";
	public static final String OTP_SEND = "OTP (One Time Password) send successfully";
	public static final String LINK_EXPIRED = "Reset OTP link expired!";
	public static final String LINK_NOT_EXPIRED = "Reset link not expired till now";

	public static final String VERIFY_TEMPLATE_ID = "d-VERIFY_TEMPLATE_ID";
	public static final String FORGOT_TEMPLATE_ID = "d-FORGOT_TEMPLATE_ID";

	public static final String PROVIDE_VALID_DYNAMIC_DATA = "provide valid dynamic data";
	public static final String TEMPLATE_TASK_USER_CREATED = "template task user created";
	public static final String NOT_FOUND_DEPT_OR_ORG = "Not able to find Department/Organisation";
	public static final String KEY_NOT_FOUND = " key not found";
	public static final String ORGANISATION_NOT_EXISTS = "organisation not exists";
	public static final String USER_NOT_LOGGED_IN = "Please login first";

}
