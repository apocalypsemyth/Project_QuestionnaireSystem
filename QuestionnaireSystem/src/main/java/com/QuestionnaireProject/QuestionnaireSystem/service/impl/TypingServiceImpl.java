package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.QuestionnaireProject.QuestionnaireSystem.entity.Typing;
import com.QuestionnaireProject.QuestionnaireSystem.repository.TypingDao;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.TypingService;

@Service
public class TypingServiceImpl implements TypingService {
	
	@Autowired
	private TypingDao typingDao;
	
	@Override
	public boolean hasTyping(
			String typingName,
			List<Typing> typingList
			) throws Exception {
		try {
			List<Typing> filteredTypingList = 
					typingList
					.stream()
					.filter(item -> item.getTypingName().equals(typingName))
					.collect(Collectors.toList());
			if (filteredTypingList == null || filteredTypingList.isEmpty()) 
				return false;
			else if (filteredTypingList.size() > 1) 
				throw new Exception("There should be only one target typing");
			else if (filteredTypingList.size() == 1) 
				return true;
			else 
				return false;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<Typing> getTypingList() throws Exception {
		try {
			List<Typing> typingList = typingDao.findAll();
			if (typingList == null || typingList.isEmpty()) return null;
			return typingList;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public void createTyping(String typingName) throws Exception {
		try {
			Typing newTyping = new Typing(typingName);
			typingDao.saveAndFlush(newTyping);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
}
