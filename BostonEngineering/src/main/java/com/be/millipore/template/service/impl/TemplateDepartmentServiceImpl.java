package com.be.millipore.template.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.template.beans.TemplateDepartment;
import com.be.millipore.template.repo.TemplateDepartmentRepo;
import com.be.millipore.template.service.TemplateDepartmentService;

@Service
public class TemplateDepartmentServiceImpl implements TemplateDepartmentService {

	@Autowired
	private TemplateDepartmentRepo departmentRepo;

	@Override
	public TemplateDepartment findByTemplateDepartmentId(Long templateDepartmentId) {
		TemplateDepartment existingTemplateDepartment = null;
		existingTemplateDepartment = departmentRepo.findByTemplateDepartmentId(templateDepartmentId);
		return existingTemplateDepartment;
	}

}
