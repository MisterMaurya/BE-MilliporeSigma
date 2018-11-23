package com.be.millipore.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import io.swagger.annotations.ApiModelProperty;

@Entity(name = "user_master")
public class User {

	@ApiModelProperty(hidden = true)
	private Long id;
	private String userName;
	private String email;
	private String fullName;
	private String title;
	private String department;
	private String countryCode;
	private String mobile;
	private Long lineManageId;
	private String password;
	private String status;
	private String otp;

	@ApiModelProperty(hidden = true)
	private String lastPassword;
	@ApiModelProperty(hidden = true)
	private boolean linkExpired;

	private Set<UserRole> role = new HashSet<UserRole>(0);

	public User() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(unique = true)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Long getLineManageId() {
		return lineManageId;
	}

	public void setLineManageId(Long lineManageId) {
		this.lineManageId = lineManageId;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role_map", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = {
			@JoinColumn(name = "userRoleId") })
	public Set<UserRole> getRole() {
		return role;
	}

	public void setRole(Set<UserRole> role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getLastPassword() {
		return lastPassword;
	}

	public void setLastPassword(String lastPassword) {
		this.lastPassword = lastPassword;
	}

	public boolean isLinkExpired() {
		return linkExpired;
	}

	public void setLinkExpired(boolean linkExpired) {
		this.linkExpired = linkExpired;
	}

}