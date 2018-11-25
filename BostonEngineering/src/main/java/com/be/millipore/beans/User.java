package com.be.millipore.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.be.millipore.constant.DBConstant;
import com.be.millipore.enums.IsActive;
import com.be.millipore.enums.IsExpired;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = DBConstant.USER_MASTER)
public class User {

	private Long id;
	private Long lineManagerId;
	private String username;
	private String email;
	private String fullName;
	private String countryCode;
	private String mobile;
	private String password;
	private String lastPassword;
	private String otp;
	private IsActive isActive = IsActive.N;
	private IsExpired IsExpired;
	private Set<UserRole> role = new HashSet<UserRole>(0);
	private Department department;

	public User() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	@Column(name = DBConstant.USER_ID, unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = DBConstant.LINE_MANAGER_ID)
	public Long getLineManagerId() {
		return lineManagerId;
	}

	public void setLineManagerId(Long lineManagerId) {
		this.lineManagerId = lineManagerId;
	}

	@Column(name = DBConstant.USERNAME)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = DBConstant.EMAIL, unique = true, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = DBConstant.FULL_NAME, nullable = false)
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = DBConstant.COUNTRY_CODE, nullable = false)
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Column(name = DBConstant.MOBILE, unique = true, nullable = false)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = DBConstant.PASSWORD, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = DBConstant.LAST_PASSWORD, nullable = false)
	@ApiModelProperty(hidden = true)
	public String getLastPassword() {
		return lastPassword;
	}

	public void setLastPassword(String lastPassword) {
		this.lastPassword = lastPassword;
	}

	@Column(name = DBConstant.OTP, nullable = false)
	@ApiModelProperty(hidden = true)
	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Column(name = DBConstant.IS_ACTIVE, nullable = false)
	@Enumerated(EnumType.STRING)
	public IsActive getIsActive() {
		return isActive;
	}

	public void setIsActive(IsActive isActive) {
		this.isActive = isActive;
	}

	@Column(name = DBConstant.IS_EXPIRED, nullable = false)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(hidden = true)
	public IsExpired getIsExpired() {
		return IsExpired;
	}

	public void setIsExpired(IsExpired isExpired) {
		IsExpired = isExpired;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = DBConstant.USER_ROLE_MAP, joinColumns = {
			@JoinColumn(name = DBConstant.USER_ID) }, inverseJoinColumns = { @JoinColumn(name = DBConstant.ROLE_ID) })
	public Set<UserRole> getRole() {
		return role;
	}

	public void setRole(Set<UserRole> role) {
		this.role = role;
	}

	@ManyToOne
	@JoinColumn(name = DBConstant.DEPARTMENT_ID)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}