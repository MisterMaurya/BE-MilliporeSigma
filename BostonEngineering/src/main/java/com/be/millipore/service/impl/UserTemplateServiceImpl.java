package com.be.millipore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.beans.UserTemplate;
import com.be.millipore.repository.UserTemplateRepo;
import com.be.millipore.service.UserTemplateService;

@Service
public class UserTemplateServiceImpl implements UserTemplateService {

	@Autowired
	private UserTemplateRepo userTemplateRepo;

	@Override
	public UserTemplate save(UserTemplate userTemplate) {
		UserTemplate existingTemplate = null;
		existingTemplate = userTemplateRepo.save(userTemplate);
		return existingTemplate;
	}
}
