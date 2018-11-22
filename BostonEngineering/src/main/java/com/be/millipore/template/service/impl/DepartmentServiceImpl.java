package com.be.millipore.template.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.template.beans.Department;
import com.be.millipore.template.repo.DepartmentRepo;
import com.be.millipore.template.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepo;

	@Override
	public Department findById(Long id) {
		Department existingDepartment = null;
		existingDepartment = departmentRepo.findById(id).get();
		return existingDepartment;
	}

}
