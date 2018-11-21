package com.be.millipore.template.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.be.millipore.template.beans.TemplateUser;

@Repository
public interface TemplateUserRepo extends CrudRepository<TemplateUser, Long> {

	public TemplateUser findByTemplateUserId(Long templateUserId);

	public TemplateUser findByEmail(String email);
}
