package com.be.millipore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.be.millipore.beans.APIAccessControl;

@Repository
public interface APIAccessControlRepo extends CrudRepository<APIAccessControl, Long> {

	public APIAccessControl findByApiName(String apiName);

}
