package com.be.millipore.service;

import java.util.List;

import com.be.millipore.beans.Template;

public interface TemplateService {

	public Template save(Template template);

	public Template findById(Long id);

	public boolean existsById(Long id);

	public List<String> getAllDynamicFields(String content);

	public Object getValue(Class<?> bean, String field, Object object) throws Exception;

}
