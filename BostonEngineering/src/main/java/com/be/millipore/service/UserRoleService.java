package com.be.millipore.service;

import java.util.List;

import com.be.millipore.beans.UserRole;

public interface UserRoleService {

	public List<UserRole> findAllUserRole();

	public boolean existsById(Long id);

	public UserRole findById(Long id);

}
