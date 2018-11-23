package com.be.millipore.template.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.millipore.template.beans.Content;
import com.be.millipore.template.repo.ContentRepo;
import com.be.millipore.template.repo.TUserRepo;
import com.be.millipore.template.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentRepo templateRepo;

	@Autowired
	private TUserRepo userRepo;

	@Override
	public Content save(Content content) {
		String stroeContent = content.getContent();
		String dynamicData[] = stroeContent.split(" ");
		for (String data : dynamicData) {
			if (data.length() > 3 && data.matches("^[${].*[}]$")) {
				String temp = data.substring(2, data.length());
				String[] array = temp.split("[.]");
				if (array.length > 1 && array.length > 3) {
					return null;
				} else if (array.length == 2) {
					/*
					 * array[0]; array[1];
					 */
				} else {
					/*
					 * array[0]; array[1]; array[1];
					 */

					// logic

				}

			}
		}

		return templateRepo.save(content);
	}

	@Override
	public Content findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}