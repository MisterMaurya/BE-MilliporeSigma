package com.be.millipore.template.service;

import com.be.millipore.beans.Template;

public interface TaskTemplateService {

	public Template save(Template template);

	public String keyNotPresent();

}
