package com.be.millipore.template.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.template.beans.TUser;
import com.be.millipore.template.repo.DepartmentRepo;
import com.be.millipore.template.repo.OrganisationRepo;
import com.be.millipore.template.repo.TUserRepo;
import com.be.millipore.template.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TUserRepo userRepo;

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private OrganisationRepo organisationRepo;

	@Override
	public TUser save(TUser user) {
		boolean existingDeparment = departmentRepo.findById(user.getDepartment().getId()).isPresent();
		if (!existingDeparment) {
			return null;
		}

		boolean existingOrganisation = organisationRepo.findById(user.getOrganisation().getId()).isPresent();
		if (!existingOrganisation) {
			return null;
		}

		return userRepo.save(user);
	}

	@Override
	public TUser findById(Long id) {
		TUser templateUser = userRepo.findById(id).get();
		return templateUser;
	}

}
