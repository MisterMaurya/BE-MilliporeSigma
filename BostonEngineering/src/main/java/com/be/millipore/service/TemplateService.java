package com.be.millipore.service;

import java.util.List;

import com.be.millipore.beans.Template;

public interface TemplateService {

	public Template save(Template template);

	public Template findById(Long id);

	// return all the dynamic data key object
	public List<String> getAllDynamicData(String content);
}