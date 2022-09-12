package com.QuestionnaireProject.QuestionnaireSystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import com.QuestionnaireProject.QuestionnaireSystem.enums.RtnInfo;
import com.QuestionnaireProject.QuestionnaireSystem.constant.DataConstant;
import com.QuestionnaireProject.QuestionnaireSystem.entity.Category;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.CategoryService;
import com.QuestionnaireProject.QuestionnaireSystem.vo.CategoryListResp;

@RestController
public class CategoryController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping(value = "/getCategoryList", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public CategoryListResp getCategoryList(
			@RequestParam(value = "is_common_question") String isCommonQuestionStr
			) {
		List<Category> categoryListResp = new ArrayList<>();
		try {
			List<Category> categoryList = categoryService.getCategoryList();
			if (categoryList == null) {
				return new CategoryListResp(
						RtnInfo.NOT_FOUND.getCode(), 
						RtnInfo.NOT_FOUND.getMessage(), 
						null
						);
			}
			
			if (StringUtils.hasText(isCommonQuestionStr)) {
				boolean isCommonQuestion = Boolean.parseBoolean(isCommonQuestionStr);
				if (isCommonQuestion) {
					categoryList = 
							categoryList
							.stream()
							.filter(item -> item.getCategoryName().equals(DataConstant.Key.COMMON_QUESTION_OF_CATEGORY))
							.collect(Collectors.toList());
				}
			}
			
			categoryListResp = categoryList;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new CategoryListResp(
					RtnInfo.FAILED.getCode(), 
					RtnInfo.FAILED.getMessage(), 
					null
					);
		}
		return new CategoryListResp(
				RtnInfo.SUCCESSFUL.getCode(), 
				RtnInfo.SUCCESSFUL.getMessage(), 
				categoryListResp
				);
	}
	
}
