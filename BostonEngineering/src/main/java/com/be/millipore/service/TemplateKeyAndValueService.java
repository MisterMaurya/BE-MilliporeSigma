package com.be.millipore.service;

import java.lang.reflect.InvocationTargetException;

import com.be.millipore.beans.TemplateKeyAndValue;
import com.be.millipore.beans.User;
import com.be.millipore.template.beans.TemplateUser;

public interface TemplateKeyAndValueService {

	public Object getUserFieldValue(String key, User existingUser) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException;

	public Object getTaskTemplateUserFieldValue(String key, TemplateUser existingTemplateUser)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException;

	public TemplateKeyAndValue findByFieldKey(String key);

}
