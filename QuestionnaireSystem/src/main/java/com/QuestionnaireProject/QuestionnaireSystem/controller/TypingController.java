package com.QuestionnaireProject.QuestionnaireSystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.QuestionnaireProject.QuestionnaireSystem.enums.RtnInfo;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Typing;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.TypingService;
import com.QuestionnaireProject.QuestionnaireSystem.vo.TypingListResp;

@RestController
public class TypingController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TypingService typingService;
	
	@GetMapping(value = "/getTypingList", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public TypingListResp getTypingList() {
		List<Typing> typingListResp = new ArrayList<>();
		try {
			List<Typing> typingList = typingService.getTypingList();
			if (typingList == null) {
				return new TypingListResp(
						RtnInfo.NOT_FOUND.getCode(), 
						RtnInfo.NOT_FOUND.getMessage(), 
						null
						);
			}
			typingListResp = typingList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new TypingListResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(), 
					null
					);
		}
		return new TypingListResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(), 
				typingListResp
				);
	}
	
}
