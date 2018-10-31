package com.be.millipore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.be.millipore.beans.UserRole;

@Repository
public interface UserRoleRepo extends CrudRepository<UserRole, Long> {

}
