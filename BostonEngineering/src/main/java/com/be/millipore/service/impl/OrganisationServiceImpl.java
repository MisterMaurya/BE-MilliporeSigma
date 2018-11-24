package com.be.millipore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.beans.Organisation;
import com.be.millipore.repository.OrganisationRepo;
import com.be.millipore.service.OrganisationService;

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

	@Override
	public Organisation save(Organisation organisation) {
		// TODO Auto-generated method stub
		return null;
	}

}