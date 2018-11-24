package com.be.millipore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.be.millipore.beans.Organisation;

@Repository
public interface OrganisationRepo extends CrudRepository<Organisation, Long> {

}