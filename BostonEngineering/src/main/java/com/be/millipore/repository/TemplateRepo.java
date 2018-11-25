package com.be.millipore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.be.millipore.beans.Template;

@Repository
public interface TemplateRepo extends CrudRepository<Template, Long> {

}
