package com.be.millipore.service.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.beans.Template;
import com.be.millipore.beans.TemplateKeyAndValue;
import com.be.millipore.repository.TemplateRepo;
import com.be.millipore.service.TemplateKeyAndValueService;
import com.be.millipore.service.TemplateService;

@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private TemplateRepo templateRepo;

	@Autowired
	TemplateKeyAndValueService keyValueService;

	private String errorFieldKey;

	public void setErrorFieldKey(String errorFieldKey) {
		this.errorFieldKey = errorFieldKey;
	}

	@Override
	public Template save(Template template) {
		Template existingTemplate = null;
		String content = template.getContent();
		List<String> dynamicFields = getAllDynamicFields(content);

		TemplateKeyAndValue existingKeyAndValue = null;
		for (String field : dynamicFields) {
			existingKeyAndValue = keyValueService.findByFieldKey(field);
			if (existingKeyAndValue == null) {
				setErrorFieldKey(field);
				System.out.println();
				return null;
			}
		}

		existingTemplate = templateRepo.save(template);
		return existingTemplate;
	}

	@Override
	public Template findById(Long id) {
		Template existingTemplate = null;
		existingTemplate = templateRepo.findById(id).get();
		return existingTemplate;
	}

	@Override
	public boolean existsById(Long id) {
		return templateRepo.existsById(id);
	}

	@Override
	public List<String> getAllDynamicFields(String content) {
		ArrayList<String> dynamicFields = new ArrayList<>();
		String data[] = content.split(" ");
		for (String dynamicData : data) {
			if (dynamicData.matches("^[${].*[}]$")) {
				dynamicFields.add(dynamicData.substring(2, dynamicData.length() - 1));
			}
		}
		return dynamicFields;
	}

	@Override
	public Object getValue(Class<?> bean, String field, Object object) throws Exception {
		return new PropertyDescriptor(field, bean).getReadMethod().invoke(object);

	}

	@Override
	public String keyNotFound() {
		return errorFieldKey;
	}

}
