package com.be.millipore.template.service;

import com.be.millipore.template.beans.TUser;

public interface UserService {

	public TUser save(TUser user);

	public TUser findById(Long id);

}
