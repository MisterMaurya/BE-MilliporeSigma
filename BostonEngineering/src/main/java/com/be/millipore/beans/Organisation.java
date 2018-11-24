package com.be.millipore.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.be.millipore.constant.DBConstant;

@Entity
@Table(name = DBConstant.ORGANISATION_MASTER)
public class Organisation {

	private Long id;
	private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = DBConstant.ORGANISATION_ID, unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = DBConstant.ORGANISATION_NAME, unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
