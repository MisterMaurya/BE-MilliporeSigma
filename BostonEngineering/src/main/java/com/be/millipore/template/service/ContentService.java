package com.be.millipore.template.service;

import com.be.millipore.template.beans.Content;

public interface ContentService {

	public Content save(Content content);

	public Content findById(Long id);
}
