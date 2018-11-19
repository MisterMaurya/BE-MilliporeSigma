package com.be.millipore.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class APIAccessControl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Long id;
	private String apiName;
	private String adminAccess;
	private String managerAccess;
	private String operatorAccess;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getAdminAccess() {
		return adminAccess;
	}

	public void setAdminAccess(String adminAccess) {
		this.adminAccess = adminAccess;
	}

	public String getManagerAccess() {
		return managerAccess;
	}

	public void setManagerAccess(String managerAccess) {
		this.managerAccess = managerAccess;
	}

	public String getOperatorAccess() {
		return operatorAccess;
	}

	public void setOperatorAccess(String operatorAccess) {
		this.operatorAccess = operatorAccess;
	}

}
