package com.be.millipore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.beans.Department;
import com.be.millipore.repository.DepartmentRepo;
import com.be.millipore.service.DepartmentService;

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

	@Override
	public Department save(Department department) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Long id) {
		return departmentRepo.existsById(id);
	}

}