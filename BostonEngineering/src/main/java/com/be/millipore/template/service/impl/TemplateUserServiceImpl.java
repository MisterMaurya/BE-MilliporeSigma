package com.be.millipore.template.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.template.beans.TemplateUser;
import com.be.millipore.template.repo.TemplateUserRepo;
import com.be.millipore.template.service.TemplateUserService;

@Service
public class TemplateUserServiceImpl implements TemplateUserService {

	@Autowired
	private TemplateUserRepo templateUserRepo;

	@Override
	public TemplateUser save(TemplateUser templateUser) {
		return templateUserRepo.save(templateUser);
	}

	@Override
	public TemplateUser findByTemplateUserId(Long templateUserId) {
		TemplateUser templateUser = templateUserRepo.findByTemplateUserId(templateUserId);
		return templateUser;
	}

}
