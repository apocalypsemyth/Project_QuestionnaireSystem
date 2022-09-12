package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import java.util.List;

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
	public boolean hasTyping(String typingName) throws Exception {
		try {
			Typing typing = typingDao.findByTypingName(typingName);
			if (typing == null) return false;
			return true;
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
