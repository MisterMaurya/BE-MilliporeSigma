package com.be.millipore.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.be.millipore.constant.DBConstant;

@Entity
@Table(name = DBConstant.USER_ROLE_MASTER)
public class UserRole {

	private Long id;
	private String role;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = DBConstant.ROLE_ID, unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = DBConstant.ROLE, unique = true, nullable = false)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
