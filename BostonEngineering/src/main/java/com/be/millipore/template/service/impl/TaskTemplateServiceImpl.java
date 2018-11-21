package com.be.millipore.template.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.beans.Template;
import com.be.millipore.beans.TemplateKeyAndValue;
import com.be.millipore.repository.TemplateKeyAndValueRepo;
import com.be.millipore.repository.TemplateRepo;
import com.be.millipore.template.service.TaskTemplateService;

@Service
public class TaskTemplateServiceImpl implements TaskTemplateService {

	private String keyNotFind;

	@Autowired
	private TemplateRepo templateRepo;

	@Autowired
	private TemplateKeyAndValueRepo keyAndValueRepo;

	@Override
	public Template save(Template template) {
		String content = template.getContent();
		TemplateKeyAndValue value = null;

		ArrayList<String> dynamicData = new ArrayList<>();
		String[] data = content.split(" ");
		StringBuffer key = null;

		for (String getDynamicData : data) {
			if (getDynamicData.startsWith("${") && getDynamicData.endsWith("}")) {
				key = new StringBuffer(getDynamicData);
				key.delete(0, 2);
				key.deleteCharAt(key.length() - 1);

				value = keyAndValueRepo.findByFieldKey(key.toString());

				if (value == null) {
					setKeyNotFind(getDynamicData);
					return null;
				}
				dynamicData.add(key.toString());
			}
			value = null;
		}
		return templateRepo.save(template);
	}

	public void setKeyNotFind(String keyNotFind) {
		this.keyNotFind = keyNotFind;
	}

	@Override
	public String keyNotPresent() {

		return keyNotFind + " is not a constant value";
	}

}
