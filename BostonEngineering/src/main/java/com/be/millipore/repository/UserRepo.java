package com.be.millipore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.be.millipore.beans.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {

	public User findByEmail(String email);

	public User findByMobile(String mobile);

	public User findByUserName(String userName);

	public User findBylineManageId(Long lineManagerId);

	// SELECT CONCAT(email,' ',mobile) FROM user_master where id=1;

	@org.springframework.data.jpa.repository.Query(value = "select  CONCAT(u.email,' ',u.mobile) from user_master u where u.id=:id")
	public String concat(@Param("id") Long id);

}
