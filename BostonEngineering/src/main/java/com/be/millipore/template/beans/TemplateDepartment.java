package com.be.millipore.template.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TemplateDepartment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long templateDepartmentId;
	private String name;

	public Long getTemplateDepartmentId() {
		return templateDepartmentId;
	}

	public void setTemplateDepartmentId(Long templateDepartmentId) {
		this.templateDepartmentId = templateDepartmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
