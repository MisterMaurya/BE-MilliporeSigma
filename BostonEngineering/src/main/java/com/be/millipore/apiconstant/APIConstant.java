package com.be.millipore.apiconstant;

public class APIConstant {

	public static final String EMAIL_ALREADY_EXISTS = "Email already exists please try with another email-id";
	public static final String MOBILE_NO_ALREADY_EXISTS = "Mobile no. already in use please use different mobile no.";
	public static final String EMAIL_NOT_EXISTS = "Please enter valid email-id";
	public static final String USER_SUCCESSFULLY_CREATED = "User successfully created";
	public static final String USER_NOT_EXISTS = "Please enter valid user id. User id is not exists";
	public static final String USER_UPDATED = "User Successfully updated";
	public static final String USER_DELETE = "User Successfully deleted";
	public static final String USERID_ALREADY_EXISTS = "UserId Already exists";
	public static final String RESPONSE_ERROR_MESSAGE = "Error message";
	public static final String USER_SUCCESSFULLY_UPDATED = "User successfully updated";
	public static final String USER_SUCCESSFULLY_DELETED = "User delete status ";
	public static final String USER_DELETE_RESPONSE = "User Successfully deleted";
	public static final String USER_CREATE_STATUS = "User create status";
	public static final String UPDATE_STATUS = "User update status";
	public static final String USER_ROLE_NOT_EXISTS = "User role not exists";
	public static final String LINE_MANAGER_IS_STATUS = "Line manager is not exists/Line manager status is inactive";
	public static final String USER_CONTROLLER_DESCRIPTON = "OPERATIONS PERTAINING TO BOSTON USER";
	public static final String USER_CONTROLLER_TAG = "USER CONTROLLER APIs";
	public static final String STATUS_RESPONSE = "status respose";
	public static final String USER_STATUS_CHANGE = "user status successfully change";
	public static final String USER_ROLE_CONTROLLER_TAG = "USER ROLE CONTROLLER APIs";
	public static final String USER_ROLE_CONTROLLER_DESCRIPTON = "OPERATIONS PERTAINING TO BOSTON USER ROLE";
	public static final String MANAGER_NAME = "manager name";
	public static final String USER_Details = "User details";

//*** REST BASE URL***//

	public static final String REST_BASE_URL = "/boston/";
	public static final String REST_USER_URL = "/user";
	public static final String REST_ID_PARAM = "/{id}";
	public static final String REST_ID_STATUS = "/status";
	public static final String REST_USER_ROLE = "/role";
	public static final String ACTIVE_USERS = "/allActive";
	public static final String ALL_ACTIVE_MANAGER = "/allActiveManager";
	public static final String USER_STATUS = "status";

//*** USER API OPERATIONS ***//	

	public static final String USER_CREATE = "Create A New User";
	public static final String USER_UPDATE = "Update A User";
	public static final String GET_ONE_USER = "Get A User";
	public static final String GET_ALL_USER = "Get All User";
	public static final String CHANGE_STATUS = "Change User Status";

//*** USER ROLE API OPERATIONS ***//

	public static final String GET_ALL_USER_ROLE = "Get All User Role";
	public static final String GET_ALL_ACIVE_USER = "Get All Active User";
	public static final String USER_IS_ENABLE = "User is status enable";
	public static final String USER_IS_DISABLE = "User is status  disable";
}
