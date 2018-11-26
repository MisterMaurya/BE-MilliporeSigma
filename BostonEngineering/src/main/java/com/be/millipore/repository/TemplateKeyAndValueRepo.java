package com.be.millipore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.be.millipore.beans.TemplateKeyAndValue;

@Repository
public interface TemplateKeyAndValueRepo extends CrudRepository<TemplateKeyAndValue, Long> {

	public TemplateKeyAndValue findByFieldKey(String key);

}
