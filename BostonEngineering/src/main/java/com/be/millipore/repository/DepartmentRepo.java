package com.be.millipore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.be.millipore.beans.Department;

@Repository
public interface DepartmentRepo extends CrudRepository<Department, Long> {

}