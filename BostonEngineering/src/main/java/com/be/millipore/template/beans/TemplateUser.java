package com.be.millipore.template.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class TemplateUser {

	private Long templateUserId;
	private String name;
	private String email;
	private Set<TemplateOrganisation> organisations = new HashSet<TemplateOrganisation>(0);
	private Set<TemplateDepartment> departments = new HashSet<TemplateDepartment>(0);

	public TemplateUser() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getTemplateUserId() {
		return templateUserId;
	}

	public void setTemplateUserId(Long templateUserId) {
		this.templateUserId = templateUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToMany(fetch = FetchType.EAGER)

	@JoinTable(name = "TemplateUser_TemplateOrganisation_map", joinColumns = {

			@JoinColumn(name = "templateUserId") }, inverseJoinColumns = {
					@JoinColumn(name = "templateOrganisationId") })
	public Set<TemplateOrganisation> getOrganisations() {
		return organisations;
	}

	public void setOrganisations(Set<TemplateOrganisation> organisations) {
		this.organisations = organisations;
	}

	@ManyToMany(fetch = FetchType.EAGER)

	@JoinTable(name = "TemplateUser_TemplateDepartment_map", joinColumns = {
			@JoinColumn(name = "templateUserId") }, inverseJoinColumns = { @JoinColumn(name = "templateDepartmentId") })
	public Set<TemplateDepartment> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<TemplateDepartment> departments) {
		this.departments = departments;
	}

}
