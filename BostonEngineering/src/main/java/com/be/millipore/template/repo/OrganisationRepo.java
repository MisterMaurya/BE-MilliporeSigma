package com.be.millipore.template.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.be.millipore.template.beans.Organisation;

@Repository
public interface OrganisationRepo extends CrudRepository<Organisation, Long> {

}
