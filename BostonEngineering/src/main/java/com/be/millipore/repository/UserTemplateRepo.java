package com.be.millipore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.be.millipore.beans.UserTemplate;

@Repository
public interface UserTemplateRepo extends CrudRepository<UserTemplate, Long> {

}
