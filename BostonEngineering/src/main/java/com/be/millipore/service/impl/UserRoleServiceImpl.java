package com.be.millipore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.beans.UserRole;
import com.be.millipore.repository.UserRoleRepo;
import com.be.millipore.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleRepo userRoleRepo;

	@Override
	public List<UserRole> findAllUserRole() {
		List<UserRole> listOfUserRole = new ArrayList<>();
		userRoleRepo.findAll().forEach(e -> listOfUserRole.add(e));
		return listOfUserRole;
	}

	@Override
	public boolean existsById(Long id) {
		return userRoleRepo.existsById(id);
	}

	@Override
	public UserRole findById(Long id) {
		boolean isExists = false;
		isExists = userRoleRepo.findById(id).isPresent();
		if (isExists == false) {
			return null;
		}
		return userRoleRepo.findById(id).get();
	}

}
