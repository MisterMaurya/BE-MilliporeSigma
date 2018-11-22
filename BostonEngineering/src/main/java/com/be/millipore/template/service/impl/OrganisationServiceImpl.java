package com.be.millipore.template.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.template.beans.Organisation;
import com.be.millipore.template.repo.OrganisationRepo;
import com.be.millipore.template.service.OrganisationService;

@Service
public class OrganisationServiceImpl implements OrganisationService {

	@Autowired
	private OrganisationRepo organisationRepo;

	@Override
	public Organisation findById(Long id) {
		Organisation existingOrganisation = null;
		existingOrganisation = organisationRepo.findById(id).get();
		return existingOrganisation;
	}

}
