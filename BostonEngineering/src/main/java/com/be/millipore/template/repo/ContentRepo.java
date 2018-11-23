package com.be.millipore.template.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.be.millipore.template.beans.Content;

@Repository
public interface ContentRepo extends CrudRepository<Content, Long> {

	@Query(value = " user_id from user_master where is_admin = 'Y' and type = 'admin'", nativeQuery = true)
	int[] getAdminDetails();

}
