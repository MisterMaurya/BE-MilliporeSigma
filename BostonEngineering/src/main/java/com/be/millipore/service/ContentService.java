package com.be.millipore.service;

import com.be.millipore.beans.Content;

public interface ContentService {

	public Content save(Content content);

	public Content findById(Long id);
}
