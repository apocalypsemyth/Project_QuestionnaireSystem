package com.QuestionnaireProject.QuestionnaireSystem.service.impl;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.QuestionnaireProject.QuestionnaireSystem.entity.CommonQuestion;
import com.QuestionnaireProject.QuestionnaireSystem.model.DataListForPager;
import com.QuestionnaireProject.QuestionnaireSystem.repository.CommonQuestionDao;
import com.QuestionnaireProject.QuestionnaireSystem.service.ifs.CommonQuestionService;

@Service
public class CommonQuestionServiceImpl implements CommonQuestionService {
	
	@Autowired
	private CommonQuestionDao commonQuestionDao;
	
	@Override
	public boolean hasCommonQuestion() throws Exception {
		try {
			List<CommonQuestion> commonQuestionList = commonQuestionDao.findAll();
			if (commonQuestionList == null || commonQuestionList.isEmpty()) return false;
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public int getCommonQuestionTotal() throws Exception {
		try {
			boolean hasCommonQuestion = hasCommonQuestion();
			if (!hasCommonQuestion) return 0;
			return commonQuestionDao.findAll().size();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public CommonQuestion getCommonQuestion(
			String commonQuestionIdStr
			) throws Exception {
		try {
			UUID commonQuestionId = UUID.fromString(commonQuestionIdStr);
			Optional<CommonQuestion> commonQuestionOp = 
					commonQuestionDao.findById(commonQuestionId);
			if (!commonQuestionOp.isPresent()) return null;
			
			return commonQuestionOp.get();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public DataListForPager getCommonQuestionList(
			String keyword, 
			int pageSize, 
			int pageIndex
			) throws Exception {
		Pageable pageReq = 
				PageRequest.of(
						pageIndex, 
						pageSize,
						Sort.by("updateDate").descending()
						);
		DataListForPager commonQuestionListObj = new DataListForPager();
		try {
			if (StringUtils.hasText(keyword)) {
				commonQuestionListObj.setTotalRows(
        				commonQuestionDao
						.findByCommonQuestionNameContaining(keyword, null).size()
						);
				commonQuestionListObj.setCommonQuestionList(
						commonQuestionDao
						.findByCommonQuestionNameContaining(keyword, pageReq)
						);
			}
			else {
				commonQuestionListObj.setTotalRows(
            			commonQuestionDao.findAll().size()
            			);
				commonQuestionListObj.setCommonQuestionList(
						commonQuestionDao.findAll(pageReq).getContent()
						);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return commonQuestionListObj;
	}

}
