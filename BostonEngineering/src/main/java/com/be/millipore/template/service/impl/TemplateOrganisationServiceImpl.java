package com.be.millipore.template.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.template.beans.TemplateOrganisation;
import com.be.millipore.template.repo.TemplateOrganisationRepo;
import com.be.millipore.template.service.TemplateOrganisationService;

@Service
public class TemplateOrganisationServiceImpl implements TemplateOrganisationService {

	@Autowired
	private TemplateOrganisationRepo organisationRepo;

	@Override
	public TemplateOrganisation findByTemplateOrganisationId(Long templateOrganisationId) {
		TemplateOrganisation existingTemplateOrganisation = null;
		existingTemplateOrganisation = organisationRepo.findByTemplateOrganisationId(templateOrganisationId);
		return existingTemplateOrganisation;
	}

}
