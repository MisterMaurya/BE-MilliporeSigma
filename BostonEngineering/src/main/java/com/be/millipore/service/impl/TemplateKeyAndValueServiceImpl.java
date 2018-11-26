package com.be.millipore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.beans.TemplateKeyAndValue;
import com.be.millipore.repository.TemplateKeyAndValueRepo;
import com.be.millipore.service.TemplateKeyAndValueService;

@Service
public class TemplateKeyAndValueServiceImpl implements TemplateKeyAndValueService {

	@Autowired
	private TemplateKeyAndValueRepo keyAndValueRepo;

	@Override
	public TemplateKeyAndValue findByFieldKey(String key) {
		TemplateKeyAndValue keyAndValue = null;
		keyAndValue = keyAndValueRepo.findByFieldKey(key);
		return keyAndValue;
	}

}
