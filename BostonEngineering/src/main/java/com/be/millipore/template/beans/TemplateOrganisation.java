package com.be.millipore.template.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TemplateOrganisation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long templateOrganisationId;
	private String name;

	public Long getTemplateOrganisationId() {
		return templateOrganisationId;
	}

	public void setTemplateOrganisationId(Long templateOrganisationId) {
		this.templateOrganisationId = templateOrganisationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
