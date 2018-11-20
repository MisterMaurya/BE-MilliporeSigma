package com.be.millipore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.beans.Template;
import com.be.millipore.repository.TemplateRepo;
import com.be.millipore.service.TemplateService;

@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private TemplateRepo templateRepo;

	@Override
	public Template save(Template template) {
		Template existingTemplate = null;
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
	public List<String> getAllDynamicData(String content) {
		ArrayList<String> dynamicData = new ArrayList<>();
		String[] data = content.split(" ");
		StringBuffer key = null;
		for (String getDynamicData : data) {
			if (getDynamicData.startsWith("${") && getDynamicData.endsWith("}")) {
				key = new StringBuffer(getDynamicData);
				key.delete(0, 2);
				key.deleteCharAt(key.length() - 1);
				dynamicData.add(key.toString());
			}
		}

		return dynamicData;
	}
}
