package com.be.millipore.constant;

public class SecurityConstant {

	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60; // 5 HOURS
	public static final String SIGNING_KEY = "lattice";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String AUTHORITIES_KEY = "scopes";
}
