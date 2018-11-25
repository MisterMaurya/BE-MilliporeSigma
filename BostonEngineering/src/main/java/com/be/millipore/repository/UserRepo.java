package com.be.millipore.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.be.millipore.beans.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {

	public User findByEmail(String email);

	public User findByMobile(String mobile);

	public User findByUsername(String userName);

	public User findByLineManagerId(Long lineManagerId);

	@Query("SELECT u from User u WHERE u.email = :email AND u.id != :user_id ")
	public User findByEmailAndId(@Param("email") String email, @Param("user_id") Long id);

}
