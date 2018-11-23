package com.be.millipore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.be.millipore.beans.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {

	public User findByEmail(String email);

	public User findByMobile(String mobile);

	public User findByUserName(String userName);

	public User findBylineManageId(Long lineManagerId);

}
