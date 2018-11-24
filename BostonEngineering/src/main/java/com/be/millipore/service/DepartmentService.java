package com.be.millipore.service;

import com.be.millipore.beans.Department;

public interface DepartmentService {

	public Department save(Department department);

	public Department findById(Long id);

	public boolean existsById(Long id);
}