package com.QuestionnaireProject.QuestionnaireSystem.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.QuestionnaireProject.QuestionnaireSystem.entity.User;
import com.QuestionnaireProject.QuestionnaireSystem.entity.UserAnswer;
import com.QuestionnaireProject.QuestionnaireSystem.enums.RtnInfo;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.UserAnswerService;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.UserService;
import com.QuestionnaireProject.QuestionnaireSystem.vo.PostUserAnswerReq;
import com.QuestionnaireProject.QuestionnaireSystem.vo.UserAnswerListResp;

@RestController
public class UserAnswerController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAnswerService userAnswerService;
	
	@PostMapping(value = "/createUserAnswer", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public UserAnswerListResp createUserAnswer(
			@RequestBody PostUserAnswerReq req, 
			HttpSession session
			) {
		List<UserAnswer> userAnswerListResp = new ArrayList<>();
		try {
			User user = userService.getUser(session);
			if (user == null) {
				return new UserAnswerListResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(), 
						null
						);
			}
			
			String questionnaireIdStr = user.getQuestionnaireId().toString();
			String userIdStr = user.getUserId().toString();
			req.setQuestionnaireId(questionnaireIdStr);
			req.setUserId(userIdStr);
			userAnswerService.createUserAnswerList(session, req);
			List<UserAnswer> userAnswerList = 
					userAnswerService.getUserAnswerList(session);
			if (userAnswerList == null) {
				return new UserAnswerListResp(
						RtnInfo.FAILED.getCode(), 
						RtnInfo.FAILED.getMessage(), 
						null
						);
			}
			userAnswerListResp = userAnswerList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new UserAnswerListResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(), 
					null
					);
		}
		return new UserAnswerListResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(), 
				userAnswerListResp
				);
	}
	
}
