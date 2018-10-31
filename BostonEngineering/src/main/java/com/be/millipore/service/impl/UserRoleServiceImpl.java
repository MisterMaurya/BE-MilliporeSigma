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
	public List<UserRole> findALLUserRole() {
		List<UserRole> listOfUser = new ArrayList<>();
		userRoleRepo.findAll().forEach(e -> listOfUser.add(e));
		return listOfUser;
	}

}
