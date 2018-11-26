package com.be.millipore.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.be.millipore.constant.DBConstant;
import com.be.millipore.enums.AdminAccessAPI;
import com.be.millipore.enums.ManagerAccessAPI;
import com.be.millipore.enums.OperatorAccessAPI;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = DBConstant.API_ACCESS_CONTROL_MASTER)
public class APIAccessControl {

	private Long id;
	private String apiName;
	private AdminAccessAPI adminAccessAPI;
	private ManagerAccessAPI managerAccessAPI;
	private OperatorAccessAPI operatorAccessAPI;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = DBConstant.API_ACCESS_ID)
	@ApiModelProperty(hidden = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = DBConstant.API_NAME, unique = true)
	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	@Column(name = DBConstant.ADMIN_ACCESS)
	@Enumerated(EnumType.STRING)
	public AdminAccessAPI getAdminAccessAPI() {
		return adminAccessAPI;
	}

	public void setAdminAccessAPI(AdminAccessAPI adminAccessAPI) {
		this.adminAccessAPI = adminAccessAPI;
	}

	@Column(name = DBConstant.MANAGER_ACCESS)
	@Enumerated(EnumType.STRING)
	public ManagerAccessAPI getManagerAccessAPI() {
		return managerAccessAPI;
	}

	public void setManagerAccessAPI(ManagerAccessAPI managerAccessAPI) {
		this.managerAccessAPI = managerAccessAPI;
	}

	@Column(name = DBConstant.OPERATOR_ACCESS)
	@Enumerated(EnumType.STRING)
	public OperatorAccessAPI getOperatorAccessAPI() {
		return operatorAccessAPI;
	}

	public void setOperatorAccessAPI(OperatorAccessAPI operatorAccessAPI) {
		this.operatorAccessAPI = operatorAccessAPI;
	}

}
