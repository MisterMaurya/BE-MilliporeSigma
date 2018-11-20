package com.be.millipore.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.beans.TemplateKeyAndValue;
import com.be.millipore.beans.User;
import com.be.millipore.repository.TemplateKeyAndValueRepo;
import com.be.millipore.service.TemplateKeyAndValueService;

@Service
public class TemplateKeyAndValueServiceImpl implements TemplateKeyAndValueService {

	@Autowired
	private TemplateKeyAndValueRepo keyAndValueRepo;

	@Override
	public TemplateKeyAndValue findByFieldKey(String key) {
		TemplateKeyAndValue keyAndValue = null;
		keyAndValue = keyAndValueRepo.findByFieldKey(key);
		return keyAndValue;
	}

	@Override
	public Object getUserFieldValue(String key, User existingUser)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException,
			NoSuchMethodException, SecurityException {
		TemplateKeyAndValue keyAndValue = findByFieldKey(key);
		if (keyAndValue == null) {
			return "${" + key + "}";
		}
		Class<?> c = Class.forName("com.be.millipore.beans.User");
		Class noparams[] = {};
		String methodName = keyAndValue.getFieldValue();

		Method method = c.getDeclaredMethod(methodName, noparams);
		Object value = method.invoke(existingUser, null);
		System.out.println(value);
		return value;
	}

}
