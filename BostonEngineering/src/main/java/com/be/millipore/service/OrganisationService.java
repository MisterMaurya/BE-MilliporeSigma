package com.be.millipore.service;

import com.be.millipore.beans.Organisation;

public interface OrganisationService {

	public Organisation save(Organisation organisation);

	public Organisation findById(Long id);

}
