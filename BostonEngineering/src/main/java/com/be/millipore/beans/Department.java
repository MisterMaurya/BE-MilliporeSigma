package com.be.millipore.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.be.millipore.constant.DBConstant;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = DBConstant.DEPARTMENT_MASTER)
public class Department {

	private Long id;
	private String name;
	private Organisation organisation;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = DBConstant.DEPARTMENT_ID, unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = DBConstant.DEPARTMENT_NAME, unique = true, nullable = false)
	@ApiModelProperty(hidden = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = DBConstant.ORGANISATION_ID)
	@ApiModelProperty(hidden = true)
	public Organisation getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organisation organisation) {
		this.organisation = organisation;
	}

}
