package com.be.millipore.template.service;

import com.be.millipore.template.beans.TemplateUser;

public interface TemplateUserService {

	public TemplateUser save(TemplateUser templateUser);

	public TemplateUser findByTemplateUserId(Long templateUserId);

}
